package com.example.litteralert.repository;

import com.example.litteralert.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByUser_username(String username);
    List<Comment> findByReport_Id(Long id);
    Comment findByReport_IdAndUser_Username(Long id, String username);
}
