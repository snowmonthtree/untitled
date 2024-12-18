package org.example.repository;

import org.example.entity.LedList;
import org.example.entity.LedListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LedListRepository extends JpaRepository<LedList, LedListId>{

    @Query("SELECT ll FROM LedList ll WHERE ll.id.playlistId = :playlistId")
    List<LedList> findByIdPlaylistId(@Param("playlistId") String playlistId);

    @Query("SELECT ll FROM LedList ll WHERE ll.id.resourceId = :resourceId")
    List<LedList> findByIdResourceId(@Param("resourceId") String resourceId);

    @Query("SELECT l FROM LedList l WHERE l.id.playlistId = :playlistId ORDER BY l.id.listNo DESC")
    List<LedList> findFirstByPlaylistIdOrderByListNoDesc(@Param("playlistId") String playlistId);

    @Query("SELECT l FROM LedList l WHERE l.id.playlistId = :playlistId ORDER BY l.id.listNo ASC")
    List<LedList> findByPlaylistIdOrderByListNoAsc(@Param("playlistId") String playlistId);
}