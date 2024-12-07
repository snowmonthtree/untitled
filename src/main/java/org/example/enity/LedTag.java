package org.example.enity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "led_tag", schema = "spot")
public class LedTag {
    @EmbeddedId
    private LedTagId id;

    @MapsId("resourceId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Resource_ID", nullable = false)
    @JsonManagedReference
    private LedResource ledResource;

    public LedTagId getId() {
        return id;
    }

    public void setId(LedTagId id) {
        this.id = id;
    }

    public LedResource getLedResource() {
        return ledResource;
    }

    public String getResourceId() {
        return ledResource != null ? ledResource.getResourceId() : null;
    }

    public void setLedResource(LedResource resource) {
        this.ledResource = resource;
    }

}