package com.example.litteralert.service;

import com.example.litteralert.model.Comment;
import com.example.litteralert.model.Report;
import com.example.litteralert.model.User;

import java.util.List;

public interface CommentService{
    void deleteById(Long id);
    Comment getCommentById(Long id);
    void createComment(String comment, User user, Report report);
    void updateComment(String comment, User user, Report report);
    List<Comment> getAll();
    List<Comment> getAllCommentsByReportId(Long id);
    List<Comment> getAllCommentsByUser_Username(String username);
}
