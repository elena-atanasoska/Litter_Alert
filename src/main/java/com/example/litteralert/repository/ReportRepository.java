package com.example.litteralert.repository;

import com.example.litteralert.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {
//    List<Report> findAllByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String text1, String text2);
    List<Report> findAllByType(String type);
    List<Report> findAllByReportStatus(String status);

    //TODO add repository methods as needed
}
