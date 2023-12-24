package com.example.litteralert.service.impl;

import com.example.litteralert.model.Report;
import com.example.litteralert.model.User;
import com.example.litteralert.model.enums.SizeOfTrash;
import com.example.litteralert.model.enums.TypeOfTrash;
import com.example.litteralert.model.exceptions.InvalidArgumentsException;
import com.example.litteralert.repository.ReportRepository;
import com.example.litteralert.repository.UserRepository;
import com.example.litteralert.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;


    public ReportServiceImpl(ReportRepository reportRepository,
                               UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> getReportByStatus(String status) {
        return reportRepository.findAllByReportStatus(status);
    }

    @Override
    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElseThrow();
    }

    @Override
    public Report createOrUpdate(String x, String y, String username, String size, String type, String description, Long id) {
        Report report = null;
        User user = userRepository.findByUsername(username).get();
        try {
            if (id == null) {
                report = new Report(Float.parseFloat(x), Float.parseFloat(y), user, SizeOfTrash.valueOf(size), TypeOfTrash.valueOf(type), description);
            } else {
                Float.parseFloat(x);
                Float.parseFloat(y);
                report = reportRepository.findById(id).get();
                report.setX(Float.parseFloat(x));
                report.setY(Float.parseFloat(y));
                report.setType(TypeOfTrash.valueOf(type));
//                report.setImage(image);
                report.setSize( SizeOfTrash.valueOf(size));
                report.setDescription(description);
            }
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException();
        }
        return reportRepository.save(report);
    }

    @Override
    public void deleteById(Long id) {
        reportRepository.deleteById(id);
    }
}
