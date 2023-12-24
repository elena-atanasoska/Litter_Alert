package com.example.litteralert.model;

import com.example.litteralert.model.enums.ReportStatus;
import com.example.litteralert.model.enums.SizeOfTrash;
import com.example.litteralert.model.enums.TypeOfTrash;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //Location
    private float x;
    private float y;

    //User
    @ManyToOne
    private User user;

    //Enums
    @Enumerated(EnumType.STRING)

    private SizeOfTrash size;
    @Enumerated(EnumType.STRING)

    private TypeOfTrash type;

    private String description;
//    private byte[] image;

    private LocalDateTime reportDate;

    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    @OneToMany(mappedBy = "report", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Comment> comments;

    public Report(float x, float y, User user, SizeOfTrash size, TypeOfTrash type, String description) {
        this.x = x;
        this.y = y;
        this.user = user;
        this.size = size;
        this.type = type;
        this.description = description;
//        this.image = image;
        this.reportDate = LocalDateTime.now();
        this.comments = new ArrayList();
        this.reportStatus = ReportStatus.CREATED;
    }

}

