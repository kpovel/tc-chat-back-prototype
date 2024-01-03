package com.example.chat.servise;

import com.example.chat.utils.exception.ErrorServerException;
import com.example.chat.model.HashtagsGroup;
import com.example.chat.model.User;
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

    public Long countHashtagGroup() {
        return repository.count();
    }

    @Transactional
    public List<HashtagsGroup> allHashtagsGroupUserLocale(User user) throws ErrorServerException {
        Optional<List<HashtagsGroup>> hashtagsGroupList = repository.findAllByLocale(user.getLocale());
        if(hashtagsGroupList.isPresent()) return hashtagsGroupList.get();
        throw new ErrorServerException("Error in method 'allHashtagsGroupUserLocale'");
    }

}
