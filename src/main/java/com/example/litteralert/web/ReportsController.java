package com.example.litteralert.web;

import com.example.litteralert.model.Comment;
import com.example.litteralert.model.Report;
import com.example.litteralert.model.enums.SizeOfTrash;
import com.example.litteralert.model.enums.TypeOfTrash;
import com.example.litteralert.model.exceptions.InvalidArgumentsException;
import com.example.litteralert.service.CommentService;
import com.example.litteralert.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping(value = {"/", "/reports"})
public class ReportsController {
    private final ReportService reportService;
    private final CommentService commentService;

    public ReportsController(ReportService reportService, CommentService commentService) {
        this.reportService = reportService;
        this.commentService = commentService;

    }

    @GetMapping("reports/all-reports")
    public String getAllReports(Model model)
    {
        List<Report> reports = this.reportService.getAllReports();
        model.addAttribute("reports", reports);
        return "allReports";
    }

    @GetMapping("reports/statistics")
    public String getStatistics(Model model)
    {
        return "statistics";
    }

    @GetMapping
    public String getHomePage(@RequestParam(required = false) String loginError,
                              @RequestParam(required = false) String registerError,
                              Model model) throws JsonProcessingException {

        List<Report> reports = this.reportService.getAllReports();

        if (loginError != null) {
            model.addAttribute("hasLoginError", true);
            model.addAttribute("loginError", loginError);
        }
        if (registerError != null) {
            model.addAttribute("hasRegisterError", true);
            model.addAttribute("registerError", registerError);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        //Set pretty printing of json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String arrayToJson = objectMapper.writeValueAsString(reports);
        model.addAttribute("reports", arrayToJson);
        model.addAttribute("sizes", SizeOfTrash.values());
        model.addAttribute("types", TypeOfTrash.values());
        return "mapa";
    }


    @PostMapping("/get-comments/{id}")
    @ResponseBody
    public String getComments(@PathVariable Long id) throws JsonProcessingException {
        Report report = reportService.getReportById(id);
        List<Comment> comments = report.getComments();
        ObjectMapper objectMapper = new ObjectMapper();
        //Set pretty printing of json
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String arrayToJson = objectMapper.writeValueAsString(comments);
        return arrayToJson;
    }



    @PostMapping("/create")
    public String createReport(@RequestParam String x,
                                 @RequestParam String y,
                                 @RequestParam String size,
                                 @RequestParam String type,
                                 @RequestParam String description,
//                                 @RequestParam(required = false) MultipartFile image,
                                 @RequestParam(required = false) Long id, Model model) throws InterruptedException, ExecutionException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

//        byte[] imageBytes = image.getBytes();

        try {
            this.reportService.createOrUpdate(x, y, username, size, type, description, id);
        } catch (InvalidArgumentsException exception) {
            model.addAttribute("hasLoginError", true);
            model.addAttribute("loginError", exception.getMessage());
            return "mapa";
        }
        return "redirect:/reports";
    }


    @GetMapping("/get/{id}")
    public Report getReportById(@PathVariable Long id) throws InterruptedException, ExecutionException {
        return reportService.getReportById(id);
    }


    @GetMapping("/delete")
    public String deleteReport(@RequestParam Long id) {
        List<Comment> comments = commentService.getAllCommentsByReportId(id);
        comments.stream().forEach(c -> commentService.deleteById(c.getId()));
        reportService.deleteById(id);
        return "redirect:/reports";
    }

}
