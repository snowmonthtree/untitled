package org.example.enity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class LedTagId implements java.io.Serializable {
    private static final long serialVersionUID = 6680797534968915798L;
    @Column(name = "Resource_ID", nullable = false, length = 11)
    private String resourceId;

    @Column(name = "Tag_ID", nullable = false, length = 11)
    private String tagId;

    public String getResourceId() {
        return resourceId;
    }

    // 必须有一个无参构造函数
    public LedTagId() {}
    public LedTagId(String resourceId, String tagId) {
        this.resourceId = resourceId;
        this.tagId = tagId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LedTagId entity = (LedTagId) o;
        return Objects.equals(this.resourceId, entity.resourceId) &&
                Objects.equals(this.tagId, entity.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId, tagId);
    }

}