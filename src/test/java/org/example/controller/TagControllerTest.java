package org.example.controller;

import org.example.repository.LedResourceRepository;
import org.example.repository.LedTagRepository;
import org.example.repository.TagRepository;
import org.example.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LedResourceRepository ledResourceRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private LedTagRepository ledTagRepository;

    @Autowired
    private TagService tagService;

    @BeforeEach
    public void setUp() {
        // 初始化测试数据
    }

    @Test
    public void testAddTag() throws Exception {
        mockMvc.perform(post("/tag/add/1/testTag"))
                .andExpect(status().isOk())
                .andExpect(content().string("Tag added successfully"));
    }

    @Test
    public void testDeleteTag() throws Exception {
        mockMvc.perform(delete("/tag")
                        .param("resourceId", "1")
                        .param("tagName", "testTag"))
                .andExpect(status().isOk())
                .andExpect(content().string("Tag deleted successfully"));
    }
}
