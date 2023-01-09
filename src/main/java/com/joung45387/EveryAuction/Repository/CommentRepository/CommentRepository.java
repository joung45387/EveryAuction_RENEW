package com.joung45387.EveryAuction.Repository.CommentRepository;

import com.joung45387.EveryAuction.Domain.DTO.ItemDTO;
import com.joung45387.EveryAuction.Domain.Model.Comment;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;

import java.util.List;

public interface CommentRepository {
    public Comment saveComment(Item item, User user, String content);
    public List<Comment> findByItem(Item item);

    Comment findById(Long id);

    void deleteComment(Comment comment);

    void editComment(Comment comment, String content);
}
