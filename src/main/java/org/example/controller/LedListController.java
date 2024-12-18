package org.example.controller;

import org.example.enity.LedResource;
import org.example.enity.PlayList;
import org.example.service.LedListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ledlist")
public class LedListController {
    @Autowired
    private LedListService ledListService;

    @PostMapping("/create-playlist")
    public ResponseEntity<String> createPlaylist(@RequestParam String userId, @RequestParam String playlistName) {
        return ResponseEntity.ok(ledListService.createPlaylist(userId, playlistName));
    }
    @PostMapping("/delete-playlist")
    public ResponseEntity<String> deletePlaylist(@RequestParam String userId, @RequestParam String playlistId) {
        return ResponseEntity.ok(ledListService.deletePlaylist(userId, playlistId));
    }
    @GetMapping("/get-playlists")
    public ResponseEntity<List<PlayList>> getPlaylists(@RequestParam String userId) {
        if (ledListService.getPlaylists(userId)!=null)
            return ResponseEntity.ok(ledListService.getPlaylists(userId));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/add-resource")
    public ResponseEntity<String> addResourceToPlaylist(@RequestParam String userId, @RequestParam String playlistId, @RequestParam String resourceId) {
        if (ledListService.addResourceToPlaylist(userId, playlistId, resourceId)!=null)
            return ResponseEntity.ok(ledListService.addResourceToPlaylist(userId, playlistId, resourceId));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    @PostMapping("/remove-last-resource")
    public ResponseEntity<String> removeLastResourceFromPlaylist(@RequestParam String userId, @RequestParam String playlistId) {
        return ResponseEntity.ok(ledListService.removeLastResourceFromPlaylist(userId, playlistId));
    }
    @GetMapping("/get-playlist-resources")
    public ResponseEntity<List<LedResource>> getPlaylistResources(@RequestParam String userId, @RequestParam String playlistId) {
        if(ledListService.getPlaylistResources(userId, playlistId)!=null)
            return ResponseEntity.ok(ledListService.getPlaylistResources(userId, playlistId));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
