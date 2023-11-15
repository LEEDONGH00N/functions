package com.example.Selenium.dto;

import com.example.Selenium.entity.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NoticeDto {

    private String title;
    private String link;
    private LocalDateTime date;
    private String views;
    public NoticeDto(Notice notice) {
        this.title = notice.getTitle();
        this.link = notice.getLink();
        this.date = notice.getDate();
        this.views = notice.getViews();
    }
}