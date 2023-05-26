package com.joung45387.EveryAuction.Repository.CommentRepository;

import com.joung45387.EveryAuction.Domain.Model.Comment;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class CommentRepositoryJPA implements CommentRepository{
    private final CommentRepositoryDataJPA commentRepositoryDataJPA;
    private final EntityManager entityManager;
    @Override
    public Comment saveComment(Item item, User user, String content) {
        Comment comment = Comment.builder()
                .item(item)
                .user(user)
                .content(content)
                .uploadTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        entityManager.persist(comment);
        return comment;
    }

    @Override
    public List<Comment> findByItem(Item item) {
        List<Comment> items = entityManager.createQuery("select c from Comment c join fetch c.user where c.item = :item", Comment.class)
                .setParameter("item", item)
                .getResultList();
        return items;
    }

    @Override
    public Comment findById(Long id){
        return entityManager.find(Comment.class, id);
    }

    @Override
    public void deleteComment(Comment comment){
        entityManager.remove(comment);
    }

    @Override
    public void editComment(Comment comment, String content){
        comment.updateContent(content);
    }
}
