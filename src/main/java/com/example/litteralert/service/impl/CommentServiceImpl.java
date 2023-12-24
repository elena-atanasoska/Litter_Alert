package com.example.litteralert.service.impl;

import com.example.litteralert.model.Comment;
import com.example.litteralert.model.Report;
import com.example.litteralert.model.User;
import com.example.litteralert.repository.CommentRepository;
import com.example.litteralert.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public void createComment(String comment, User user, Report report){
        commentRepository.save(new Comment(comment,user, report));
    }

    @Override
    public void updateComment(String comment, User user, Report report){
        Comment c = commentRepository.findByReport_IdAndUser_Username(report.getId(),user.getUsername());
        c.setComment(comment);
        commentRepository.save(c);
    }

    @Override
    public List<Comment> getAll(){
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(Long id){
        return commentRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getAllCommentsByReportId(Long id){
        return commentRepository.findByReport_Id(id);
    }

    @Override
    public List<Comment> getAllCommentsByUser_Username(String username){
        return commentRepository.findByUser_username(username);
    }
}
