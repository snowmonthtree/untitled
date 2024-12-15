package org.example.controller;

import org.example.enity.LedResource;
import org.example.enity.PlayRecord;
import org.example.enity.User;
import org.example.repository.LedResourceRepository;
import org.example.repository.PlayRecordRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayRecordControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LedResourceRepository ledResourceRepository;

    @Autowired
    private PlayRecordRepository playRecordRepository;

    private User testUser;
    private LedResource test;

    @BeforeEach
    void setUp() {
        testUser=userRepository.findByUserId("testUser");
        test=ledResourceRepository.findByResourceId("1");
        // 清空数据
    }

    @Test
    void testAddPlayRecord() {
        // 发送请求
        ResponseEntity<Void> response = restTemplate.exchange(
                "/play-records/add/" + testUser.getUserId() + "/" + test.getResourceId(),
                HttpMethod.POST,
                null,
                Void.class
        );

        // 验证响应
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // 验证数据库
        List<PlayRecord> playRecords = playRecordRepository.findByUser_UserIdOrderByPlayTimeDesc(testUser.getUserId());
        assertThat(playRecords.size()).isEqualTo(1);
        assertThat(playRecords.get(0).getLedResource().getResourceId()).isEqualTo(test.getResourceId());
        assertThat(playRecords.get(0).getUser().getUserId()).isEqualTo(testUser.getUserId());
    }

}
