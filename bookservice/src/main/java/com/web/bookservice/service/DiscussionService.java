package com.web.bookservice.service;

import com.web.bookservice.domain.Discussion;
import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.DiscussionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DiscussionService {

    private final DiscussionRepository repository;

    public Long save(Discussion discussion) {
        return repository.save(discussion);
    }

    public Optional<Discussion> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteDiscussion(Long discussionIndex) {
        repository.deleteDiscussion(discussionIndex);
    }

    public void update(Long discussionIndex, String title, String text) {
        Optional<Discussion> findDiscussion = findById(discussionIndex);

        findDiscussion.get().setTitle(title);
        findDiscussion.get().setText(text);

        repository.save(findDiscussion.get());
    }

    public List<Discussion> findAll() {
        return repository.findAll();
    }

    public Page<Discussion> getDataByPageAndMember(String memberId,String select, String query, int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);


        if(select == null) {
            return repository.getAllDataByPageAndMember(memberId, pageable);
        } else if(select.equals("title")){
            return repository.getTitleByPageAndMember(query, memberId ,pageable);
        } else if (select.equals("bookName")) {
            return repository.getBookTitleByPageAndMember(query,memberId ,pageable);
        } else if (select.equals("writer")) {
            return repository.getMemberNameByPageAndMember(query, memberId ,pageable);
        }

        return  null;
    }

    public Page<Discussion> getDataByPage(String select, String query, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        if(select == null) {
            return repository.getAllDataByPage(pageable);
        } else if(select.equals("title")){
            return repository.getTitleByPage(query,pageable);
        } else if (select.equals("bookName")) {
            return repository.getBookTitleByPage(query, pageable);
        } else if (select.equals("writer")) {
            return repository.getMemberNameByPage(query, pageable);
        }

        return null;
    }
}
