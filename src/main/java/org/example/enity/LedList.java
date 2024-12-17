package org.example.enity;

import jakarta.persistence.*;

@Entity
@Table(name = "led_list")
public class LedList {
    @EmbeddedId
    private LedListId id;

    @MapsId("resourceId")
    @ManyToOne
    @JoinColumn(name = "Resource_ID", nullable = false)
    private LedResource resource;

    public LedListId getId() {
        return id;
    }

    public void setId(LedListId id) {
        this.id = id;
    }

    public LedResource getResource() {
        return resource;
    }

    public void setResource(LedResource resource) {
        this.resource = resource;
    }

}