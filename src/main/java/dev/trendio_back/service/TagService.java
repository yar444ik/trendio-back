package dev.trendio_back.service;

import dev.trendio_back.dto.TagDto;
import dev.trendio_back.dto.mapper.TagMapper;
import dev.trendio_back.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagService {
/*    todo: пофиксить баг с повторением айдишников тегов в PUT запросе реквесте
*      к примеру: 1. указывать айдишники тегов в json принудительно
*                 2. делать автоматическую подстановку через findById
*                    c операцией присваивания
*/
//    private final TagRepository tagRepository;
//
//    private final TagMapper tagMapper;
//
//    public List<TagDto> findAll() {
//        return tagRepository.findAll().
//    }
}
