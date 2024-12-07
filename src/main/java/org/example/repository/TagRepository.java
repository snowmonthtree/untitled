package org.example.repository;

import org.example.enity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    // 可以在这里添加自定义查询方法
    Optional<Tag> findByTagName(String tagName);
    Tag findByTagId(String tagId);
}

