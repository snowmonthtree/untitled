package org.example.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;

import org.example.enity.LedResource;
import org.example.enity.LedList;
import org.example.enity.LedListId;
import org.example.enity.PlayList;
import org.example.enity.User;
import org.example.repository.LedListRepository;
import org.example.repository.LedResourceRepository;
import org.example.repository.PlayListRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LedListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private LedResourceRepository ledResourceRepository;

    @Autowired
    private LedListRepository ledListRepository;

    private User user;
    private PlayList playList;
    private LedResource ledResource;

    @BeforeEach
    public void setUp() {
        user=userRepository.findByUserId("1");
        ledResource = ledResourceRepository.findByResourceId("1");

        playList = new PlayList();
        playList.setPlaylistId("1");
        playList.setUser(user);
        playList.setPlaylistName("Test");

        playList.setPlaylistType("public");
        playList.setCreateTime(new Timestamp(System.currentTimeMillis()));
        playListRepository.save(playList);
    }

    @Test
    public void testCreatePlaylist() throws Exception {
        mockMvc.perform(post("/ledlist/create-playlist")
                        .param("userId", "1")
                        .param("playlistName", "New Playlist"))
                .andExpect(status().isOk())
                .andExpect(content().string("Playlist created successfully"));

        // 验证数据库中的数据
        PlayList createdPlayList = playListRepository.findByPlaylistId("2"); // 新创建的播放列表ID应该是2
        assertThat(createdPlayList).isNotNull();
        assertThat(createdPlayList.getPlaylistName()).isEqualTo("New Playlist");
        assertThat(createdPlayList.getUser().getUserId()).isEqualTo("1");
    }

    @Test
    public void testAddResourceToPlaylist() throws Exception {
        mockMvc.perform(post("/ledlist/add-resource")
                        .param("userId", "1")
                        .param("playlistId", "816de787-5")
                        .param("resourceId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Resource added to playlist successfully"));

        // 验证数据库中的数据
        LedListId ledListId = new LedListId();
        ledListId.setPlaylistId("1");
        ledListId.setResourceId("1");
        ledListId.setListNo(1);

        LedList ledList = ledListRepository.findById(ledListId).orElse(null);
        assertThat(ledList).isNotNull();
        assertThat(ledList.getResource().getResourceId()).isEqualTo("1");
    }

    @Test
    public void testRemoveResourceFromPlaylist() throws Exception {
        mockMvc.perform(post("/ledlist/remove-last-resource")
                        .param("userId", "1")
                        .param("playlistId", "eabde6b1-6")
                        )
                .andExpect(status().isOk());

    }
}
