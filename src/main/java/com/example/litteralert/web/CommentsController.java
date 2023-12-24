package com.example.litteralert.web;

import com.example.litteralert.model.Comment;
import com.example.litteralert.model.Report;
import com.example.litteralert.model.User;
import com.example.litteralert.repository.UserRepository;
import com.example.litteralert.service.CommentService;
import com.example.litteralert.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentsController {
    private final CommentService commentService;
    private final ReportService reportService;
    private final UserRepository userRepository;

    public CommentsController(CommentService commentService, ReportService reportService,
                              UserRepository userRepository) {
        this.commentService = commentService;
        this.reportService = reportService;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String getAllComments(@RequestParam(required = false) String error, Model model) {
        List<Comment> comments = this.commentService.getAll();
        model.addAttribute("comments", comments);
        model.addAttribute("error", error);
        return "allComments";
    }

    @PostMapping("/{id}")
    @ResponseBody
    public List<Comment> getAllCommentsForreport(@PathVariable Long id, @RequestParam(required = false) String error,
                                                 Model model) throws JsonProcessingException {
        List<Comment> comments = this.commentService.getAllCommentsByReportId(id);
        model.addAttribute("error", error);
        ObjectMapper objectMapper = new ObjectMapper();
        //Set pretty printing of json
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String arrayToJson = objectMapper.writeValueAsString(comments);
        model.addAttribute("comments", arrayToJson);
        return comments;
    }

    @GetMapping("/add-comment/{id}")
    public String addCommentPage(@PathVariable Long id,
                                 @RequestParam(required = false) String error, Model model) {
        Report report = this.reportService.getReportById(id);
        model.addAttribute("error", error);
        model.addAttribute("report", report);
        return "add-comment";
    }

    @PostMapping("/add-comment/{id}")
    public String createComment(@PathVariable String id, @RequestParam String comment,
                                @RequestParam String name, Model model, @RequestParam(required = false) String error) {
        Report report = this.reportService.getReportById(Long.valueOf(id));
        model.addAttribute("error", error);
        model.addAttribute("report", report);
        User user = userRepository.findByName(name).get();
        this.commentService.createComment(comment, user, report);
        return "redirect:/reports";
    }

    @PostMapping("/delete-comment/{id}")
    public String deleteCommentTestingTesting(@PathVariable String id) {
        this.commentService.deleteById(Long.valueOf(id));
        return "redirect:/reports";
    }
}