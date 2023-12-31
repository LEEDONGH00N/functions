package com.example.Selenium.service;

import com.example.Selenium.dto.NoticeDto;
import com.example.Selenium.entity.Notice;
import com.example.Selenium.repo.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpbitCrawlerService {

    private final ModelMapper modelMapper;
    private final NoticeRepository noticeRepository;
    public List<NoticeDto> crawlUpbitNotices(){
        // 크롬 드라이버 설정
        System.setProperty("webdriver.chrome.driver",
                "/Users/leedonghoon/Desktop/functions/Selenium/chromedriver");

        // 크롬 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);

        // Upbit 공지사항 페이지 접속
        driver.get("https://upbit.com/service_center/notice");

        // 공지사항 목록 가져옴
        List<WebElement> elements = driver.findElements(By.cssSelector("css-tevlmt > tbody"));

        // 반환할 NoticeDto 리스트 생성
        List<NoticeDto> notices = new ArrayList<>();
        // 각 공지사항에 대하여
        // https://www.selenium.dev/documentation/webdriver/elements/finders/#find-elements-from-element
        for (WebElement noticeElement : elements) {
            // 공지사항의 제목, 링크를 가져옵니다.
            WebElement anchorElement = noticeElement.findElement(By.cssSelector("td:nth-child(1) > a"));
            String title = anchorElement.getText(); // "a" 태그의 텍스트(제목)를 가져옵니다.
            String link = anchorElement.getAttribute("href"); // "a" 태그의 "href" 속성(링크)를 가져옵니다.
            // 제목과 링크 출력
            log.info(title);
            log.info(link);

            // 공지사항의 날짜, 조회수를 가져옵니다.
            // "td:nth-child(2)"는 "td" 태그 중 두 번째 자식에 해당하는 태그를 선택합니다.
            WebElement dateElement = noticeElement.findElement(By.cssSelector("td:nth-child(2)"));
            String dateText = dateElement.getText(); // "td" 태그의 텍스트(날짜)를 가져옵니다.
            LocalDateTime date = LocalDateTime.parse(dateText, DateTimeFormatter.ofPattern("yyyy.MM.dd")); // 날짜 문자열을 LocalDate 객체로 변환합니다.

            String views = noticeElement.findElement(By.cssSelector("td:nth-child(3)")).getText();

            // Notice 객체 생성 및 저장
            Notice notice = Notice.builder()
                    .title(title)
                    .link(link)
                    .date(date)
                    .views(views)
                    .build();

            // Notice 객체를 DB에 저장합니다.
            this.noticeRepository.save(notice);
            // 저장된 Notice 객체를 NoticeDto로 변환하여 리스트에 추가합니다.
            notices.add(new NoticeDto(notice));
        }
        // WebDriver 종료
        driver.quit();
        // NoticeDto 리스트 반환
        return notices;
    }
    public List<NoticeDto> showAll(){
        List<Notice> list = this.noticeRepository.findAll();
        return list
                .stream()
                .map(noticeList -> modelMapper.map(noticeList, NoticeDto.class))
                .collect(Collectors.toList());

    }

}
