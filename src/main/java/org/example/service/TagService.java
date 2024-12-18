package org.example.service;

import org.springframework.http.ResponseEntity;

public interface TagService {
    ResponseEntity<String> addTag(String resourceId, String tagName);
    ResponseEntity<String> deleteTag(String resourceId, String tagName);
}
