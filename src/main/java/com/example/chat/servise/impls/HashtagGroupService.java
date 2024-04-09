package com.example.chat.servise.impls;

import com.example.chat.utils.exception.ErrorServerException;
import com.example.chat.model.HashtagsGroup;
import com.example.chat.repository.HashtagGroupRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HashtagGroupService {
    private final HashtagGroupRepository repository;

    public void saveHashtagGroup(HashtagsGroup hashtagsGroup) {
        repository.save(hashtagsGroup);
    }

    public long countHashtagGroup() {
        return repository.count();
    }

    @Transactional
    public List<HashtagsGroup> getAllHashtagsGroupsByLocale(String lang) throws ErrorServerException {
        Optional<List<HashtagsGroup>> hashtagsGroupList = repository.findAllByLocale(lang);
        if(hashtagsGroupList.isPresent()) return hashtagsGroupList.get();
        else throw new ErrorServerException("Error in method 'allHashtagsGroupUserLocale'");
    }

}
