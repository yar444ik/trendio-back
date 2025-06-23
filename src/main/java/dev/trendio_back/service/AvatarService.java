package dev.trendio_back.service;

import dev.trendio_back.dto.ImageDto;
import dev.trendio_back.dto.mapper.ImageMapper;
import dev.trendio_back.entity.ImageEntity;
import dev.trendio_back.entity.auth.UserEntity;
import dev.trendio_back.exception.NotFoundException;
import dev.trendio_back.repository.ImageRepository;
import dev.trendio_back.repository.UserRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

    public String uploadFile(MultipartFile file, Long userId) {
        try {
            String fileName = file.getOriginalFilename();
            String newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(newFileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            String imageUrl =  minioUrl + bucket + "/" + newFileName;
            ImageEntity imageEntity = create(ImageDto.builder().imageUrl(imageUrl).build());
            UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User не найден"));
            user.setAvatar(imageEntity);
            userRepository.save(user);
            //todo     check transaction, ACID, you put file into minio, and got exception in userRepository.save,
            // what happened with file, which saved in minio
            return newFileName;
        }
        catch (Exception e) {
            throw new RuntimeException("upload fail ", e);
        }
    }

    public String getFileUrl(String fileName) {
        return minioUrl + "/" + bucket + "/" + fileName;
    }

    public ImageEntity create(ImageDto imageDto) {
        return imageRepository.save(imageMapper.dtoToEntity(imageDto));
    }
}
