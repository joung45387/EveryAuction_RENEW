package com.joung45387.EveryAuction.Repository.CommentRepository;

import com.joung45387.EveryAuction.Domain.Model.BidRecord;
import com.joung45387.EveryAuction.Domain.Model.Comment;
import com.joung45387.EveryAuction.Domain.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepositoryDataJPA extends JpaRepository<Comment, Long> {
    public List<Comment> findByItem(Item item);
}
