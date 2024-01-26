package com.web.bookservice.service;

import com.web.bookservice.domain.Discussion;
import com.web.bookservice.repository.jpa.DiscussionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DiscussionService {

    private final DiscussionRepository repository;

    public Long save(Discussion discussion) {
        return repository.save(discussion).getId();
    }

    public Discussion findById(Long id) {
        return repository.findById(id).get();
    }

    public void deleteDiscussion(Discussion discussion) {
        repository.delete(discussion);
    }

    public void update(Long discussionIndex, String title, String text) {
        Discussion findDiscussion = findById(discussionIndex);

        findDiscussion.setTitle(title);
        findDiscussion.setText(text);

        repository.save(findDiscussion);
    }


    public List<Discussion> findAll() {
        return repository.findAll();
    }

    public Page<Discussion> getDataByPageAndMember(String loginId,String select, String query, int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);


        if(select == null) {
            log.info("여기");
            return repository.findByMember_LoginId(loginId, pageable);
        } else if(select.equals("title")){
            return repository.findByTitleContainingAndMember_LoginId(query,loginId, pageable);
        } else if (select.equals("bookName")) {
            return repository.findByBook_TitleContainingAndMember_LoginId(query, loginId, pageable);
        } else if (select.equals("writer")) {
            return repository.findByMember_NameContainingAndMember_LoginId(query, loginId, pageable);
        }

        return  null;
    }

    public Page<Discussion> getDataByPage(String select, String query, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        if(select == null) {
            return repository.findAll(pageable);
        } else if(select.equals("title")){
            return repository.findByTitleContaining(query, pageable);
        } else if (select.equals("bookName")) {
            return repository.findByBook_TitleContaining(query, pageable);
        } else if (select.equals("writer")) {
            return repository.findByMember_NameContaining(query, pageable);
        }

        return null;
    }
}
