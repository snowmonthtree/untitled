package org.example.controller;

import org.example.enity.LedResource;
import org.example.enity.LedList;
import org.example.enity.LedListId;
import org.example.enity.PlayList;
import org.example.enity.User;
import org.example.repository.LedListRepository;
import org.example.repository.LedResourceRepository;
import org.example.repository.PlayListRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/ledlist")
public class LedListController {
    @Autowired
    private LedListRepository ledListRepository;
    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private PlayListRepository playListRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create-playlist")
    public ResponseEntity<String> createPlaylist(@RequestParam String userId, @RequestParam String playlistName) {
        PlayList playList = new PlayList();
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        playList.setUser(user);
        playList.setPlaylistName(playlistName);
        playList.setPlaylistType("public");
        playList.setCreateTime(new Timestamp(System.currentTimeMillis()));
        playListRepository.save(playList);
        return ResponseEntity.ok("Playlist created successfully");
    }
    @PostMapping("/delete-playlist")
    public ResponseEntity<String> deletePlaylist(@RequestParam String userId, @RequestParam String playlistId) {

        PlayList playList = playListRepository.findByPlaylistId(playlistId);
        if (playList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
        }
        List<LedList> ledLists = ledListRepository.findByIdPlaylistId(playlistId);
        for (LedList ledList : ledLists) {
            ledListRepository.delete(ledList);
        }
        playListRepository.delete(playList);
        return ResponseEntity.ok("Playlist deleted successfully");

    }

    @PostMapping("/add-resource")
    public ResponseEntity<String> addResourceToPlaylist(@RequestParam String userId, @RequestParam String playlistId, @RequestParam String resourceId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        PlayList playList = playListRepository.findByPlaylistId(playlistId);
        if (playList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
        }
        LedResource ledResource = ledResourceRepository.findByResourceId(resourceId);
        if (ledResource == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
        }

        List<LedList> existingResources = ledListRepository.findByIdPlaylistId(playlistId);
        for (LedList ledList : existingResources) {
            if (ledList.getResource().getResourceId().equals(resourceId)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Resource already in playlist");
            }
        }
        LedList ledList = new LedList();
        LedListId ledListId = new LedListId();
        ledListId.setResourceId(resourceId);
        ledListId.setPlaylistId(playlistId);
        ledListId.setListNo(existingResources.size() + 1); // Assuming listNo is based on the number of resources in the playlist

        ledList.setId(ledListId);
        ledList.setResource(ledResource);
        ledListRepository.save(ledList);
        return ResponseEntity.ok("Resource added to playlist successfully");
    }
    @PostMapping("/remove-last-resource")
    public ResponseEntity<String> removeLastResourceFromPlaylist(@RequestParam String userId, @RequestParam String playlistId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        PlayList playList = playListRepository.findByPlaylistId(playlistId);
        if (playList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
        }
        List<LedList> lastLedListList= ledListRepository.findFirstByPlaylistIdOrderByListNoDesc(playlistId);
        LedList lastLedList = lastLedListList.get(0);
        ledListRepository.delete(lastLedList);
        return ResponseEntity.ok("Last resource removed from playlist successfully");
    }

}
