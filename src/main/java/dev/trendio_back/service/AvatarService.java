package dev.trendio_back.service;

import dev.trendio_back.dto.ImageDto;
import dev.trendio_back.dto.mapper.ImageMapper;
import dev.trendio_back.entity.ImageEntity;
import dev.trendio_back.entity.auth.UserEntity;
import dev.trendio_back.exception.ExistsException;
import dev.trendio_back.exception.NotFoundException;
import dev.trendio_back.repository.ImageRepository;
import dev.trendio_back.repository.UserRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AvatarService {

    private final MinioClient minioClient;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.endpoint}")
    private String minioUrl;

    @Transactional
    public String uploadFile(MultipartFile file, Long userId) {
        String newFileName = null;
        try {
            String fileName = file.getOriginalFilename();
            newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucket)
                                .object(newFileName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }
            try {
                String imageUrl = minioUrl + bucket + "/" + newFileName;
                ImageEntity imageEntity = create(ImageDto.builder().imageUrl(imageUrl).build());
                UserEntity user = userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("User не найден"));
                user.setAvatar(imageEntity);
                userRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                if (e.getCause() instanceof PSQLException psqlEx) {
                    if ("23505".equals(psqlEx.getSQLState())) {
                        throw new ExistsException(e.getMessage());
                    } else {
                        throw e;
                    }
                }
            }
        } catch (Exception e) {
            if (newFileName != null) {
                cleanupMinioFile(newFileName);
            }

            if (e instanceof ExistsException) {
                throw new ExistsException(e.getMessage());
            }
        }
        return newFileName;
    }

    private void cleanupMinioFile(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to cleanup MinIO file: " + fileName, e);
        }
    }

    public String getFileUrl(String fileName) {
        return minioUrl + "/" + bucket + "/" + fileName;
    }

    public ImageEntity create(ImageDto imageDto) {
        return imageRepository.save(imageMapper.dtoToEntity(imageDto));
    }
}
