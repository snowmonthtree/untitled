package org.example.service;

import org.example.entity.*;
import org.example.repository.LedListRepository;
import org.example.repository.LedResourceRepository;
import org.example.repository.PlayListRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class LedListServiceImpl implements LedListService{
    @Autowired
    private LedListRepository ledListRepository;
    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private PlayListRepository playListRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String createPlaylist(String userId, String playlistName) {
        PlayList playList = new PlayList();
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "user is null";
        }
        playList.setUser(user);
        playList.setPlaylistName(playlistName);
        playList.setPlaylistType("public");
        playList.setCreateTime(new Timestamp(System.currentTimeMillis()));
        playListRepository.save(playList);
        return "Playlist created successfully";
    }
    @Override
    public String deletePlaylist(String userId, String playlistId) {

        PlayList playList = playListRepository.findByPlaylistId(playlistId);
        if (playList == null) {
            return "Playlist not found";
        }
        List<LedList> ledLists = ledListRepository.findByIdPlaylistId(playlistId);
        for (LedList ledList : ledLists) {
            ledListRepository.delete(ledList);
        }
        playListRepository.delete(playList);
        return "Playlist deleted successfully";
    }
    @Override
    public List<PlayList> getPlaylists(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return null;
        }
        List<PlayList> playLists = playListRepository.findByUser_UserId(userId);
        return playLists;
    }

    @Override
    public String addResourceToPlaylist(String userId, String playlistId, String resourceId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "User not found";
        }
        PlayList playList = playListRepository.findByPlaylistId(playlistId);
        if (playList == null) {
            return "Playlist not found";
        }
        LedResource ledResource = ledResourceRepository.findByResourceId(resourceId);
        if (ledResource == null) {
            return "Resource not found";
        }

        List<LedList> existingResources = ledListRepository.findByIdPlaylistId(playlistId);
        for (LedList ledList : existingResources) {
            if (ledList.getResource().getResourceId().equals(resourceId)) {
                return "Resource already in playlist";
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
        return "Resource added to playlist successfully";
    }
    @Override
    public String removeLastResourceFromPlaylist(String userId, String playlistId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "User not found";
        }
        PlayList playList = playListRepository.findByPlaylistId(playlistId);
        if (playList == null) {
            return "Playlist not found";
        }
        List<LedList> lastLedListList= ledListRepository.findFirstByPlaylistIdOrderByListNoDesc(playlistId);
        LedList lastLedList = lastLedListList.get(0);
        ledListRepository.delete(lastLedList);
        return "Last resource removed from playlist successfully";
    }
    @Override
    public List<LedResource> getPlaylistResources(String userId, String playlistId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return null;
        }
        PlayList playList = playListRepository.findByPlaylistId(playlistId);
        if (playList == null) {
            return null;
        }
        List<LedList> ledLists = ledListRepository.findByPlaylistIdOrderByListNoAsc(playlistId);
        List<LedResource> ledResources = ledLists.stream()
                .map(LedList::getResource)
                .toList();
        return ledResources;
    }

}
