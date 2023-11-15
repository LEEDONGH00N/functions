package com.example.Selenium.controller;

import com.example.Selenium.dto.NoticeDto;
import com.example.Selenium.service.UpbitCrawlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/crawl")
public class UpbitCrawlerController {
    private final UpbitCrawlerService upbitCrawlerService;
    @GetMapping("/upbit/notices")
    public List<NoticeDto> crawlUpbitNotices() {
        return this.upbitCrawlerService.crawlUpbitNotices();
    }
    @GetMapping("/show-all/notices")
    public List<NoticeDto> showCrawledNotices(){
        return this.upbitCrawlerService.showAll();
    }
}