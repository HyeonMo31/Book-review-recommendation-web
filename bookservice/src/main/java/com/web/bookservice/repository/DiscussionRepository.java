package com.web.bookservice.repository;

import com.web.bookservice.domain.Discussion;
import com.web.bookservice.repository.jpa.SpringDataJpaDiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DiscussionRepository {

    private final SpringDataJpaDiscussionRepository repository;

    public Long save(Discussion discussion) {

        return repository.save(discussion).getDiscussionIndex();
    }

    public Optional<Discussion> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteDiscussion(Long discussionIndex) {
        repository.deleteById(discussionIndex);
    }

    public List<Discussion> findAll() {
        return repository.findAll();
    }

    public Page<Discussion> getTitleByPage(String query ,Pageable pageable) {
        return repository.findByTitleContaining(query, pageable);
    }

    public Page<Discussion> getBookTitleByPage(String query ,Pageable pageable) {
        return repository.findByBook_TitleContaining(query, pageable);
    }

    public Page<Discussion> getMemberNameByPage(String query ,Pageable pageable) {
        return repository.findByMember_NameContaining(query, pageable);
    }

    public Page<Discussion> getAllDataByPage(Pageable pageable) {
        return  repository.findAll(pageable);
    }

    public Page<Discussion> getTitleByPageAndMember(String title, String memberId ,Pageable pageable) {
        return repository.findByTitleContainingAndMember_Id(title,memberId, pageable);
    }

    public Page<Discussion> getBookTitleByPageAndMember(String bookTitle, String memberId ,Pageable pageable) {
        return repository.findByBook_TitleContainingAndMember_Id(bookTitle, memberId, pageable);
    }

    public Page<Discussion> getMemberNameByPageAndMember(String memberName, String memberId ,Pageable pageable) {
        return repository.findByMember_NameContainingAndMember_Id(memberName, memberId, pageable);
    }

    public Page<Discussion> getAllDataByPageAndMember(String memberId, Pageable pageable) {
        return  repository.findByMember_Id(memberId, pageable);
    }

}
