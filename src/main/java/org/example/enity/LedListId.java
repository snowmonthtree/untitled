package org.example.enity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class LedListId implements java.io.Serializable {
    private static final long serialVersionUID = -4580893799408005187L;
    @Column(name = "Resource_ID", nullable = false, length = 11)
    private String resourceId;

    @Column(name = "Playlist_ID", nullable = false, length = 11)
    private String playlistId;

    @Column(name = "List_No", nullable = false)
    private Integer listNo;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public Integer getListNo() {
        return listNo;
    }

    public void setListNo(Integer listNo) {
        this.listNo = listNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LedListId entity = (LedListId) o;
        return Objects.equals(this.playlistId, entity.playlistId) &&
                Objects.equals(this.resourceId, entity.resourceId) &&
                Objects.equals(this.listNo, entity.listNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId, resourceId, listNo);
    }

}