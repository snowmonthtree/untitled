package org.example.controller;

import org.example.repository.LedResourceRepository;
import org.example.repository.LedTagRepository;
import org.example.repository.TagRepository;
import org.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private LedTagRepository ledTagRepository;
    @Autowired
    private TagService tagService;

    @PostMapping("/add/{resourceId}/{tagName}")
    public ResponseEntity<String> addTag(@PathVariable String resourceId, @PathVariable String tagName) {
        return tagService.addTag(resourceId, tagName);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteTag(@RequestParam String resourceId, @RequestParam String tagName) {
        return tagService.deleteTag(resourceId, tagName);
    }
}
