package org.example.service;

public interface TagService {
    String addTag(String resourceId, String tagName);
    String deleteTag(String resourceId, String tagName);
}
