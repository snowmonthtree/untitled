package org.example.service;

import org.example.entity.LedResource;
import org.example.entity.PlayList;

import java.util.List;

public interface LedListService {
    String createPlaylist(String userId, String playlistName);
    String deletePlaylist(String userId, String playlistId);
    String addResourceToPlaylist(String userId, String playlistId, String resourceId);
    String removeResourceFromPlaylist(String userId, String playlistId,String resourceId);
    List<LedResource> getPlaylistResources(String userId, String playlistId);
    List<PlayList> getPlaylists(String userId);
}
