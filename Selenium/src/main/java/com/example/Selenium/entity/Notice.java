package com.example.Selenium.entity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String link;
    private String views;
    @Column(nullable = false)
    private LocalDateTime date;

    @Builder
    public Notice(String title, String link, LocalDateTime date, String views) {
        this.title = title;
        this.link = link;
        this.date = date;
        this.views = views;
    }
}