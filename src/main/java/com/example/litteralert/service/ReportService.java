package com.example.litteralert.service;

import com.example.litteralert.model.Report;

import java.util.List;

public interface ReportService{
    List<Report> getAllReports();
    List<Report> getReportByStatus(String status);

    Report getReportById(Long id);

    Report createOrUpdate(String x, String y, String username, String size, String type, String description, Long id);

    void deleteById(Long id);

}
