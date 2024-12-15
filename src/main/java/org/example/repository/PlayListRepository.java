package org.example.repository;


import org.example.enity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, String> {

    List<PlayList> findByUser_UserId(String userId);
    PlayList findByPlaylistId(String playlistId);
}