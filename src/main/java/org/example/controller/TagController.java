package org.example.controller;

import org.example.enity.LedResource;
import org.example.enity.LedTag;
import org.example.enity.LedTagId;
import org.example.enity.Tag;
import org.example.repository.LedResourceRepository;
import org.example.repository.LedTagRepository;
import org.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private LedTagRepository ledTagRepository;

    @PostMapping("/add/{resourceId}/{tagName}")
    public ResponseEntity<String> addTag(@PathVariable String resourceId, @PathVariable String tagName) {
        // 查找 LedResource
        Optional<LedResource> ledResourceOptional = ledResourceRepository.findById(resourceId);
        Optional<Tag> tagOptional = tagRepository.findByTagName(tagName);
        String tagId;
        if (!ledResourceOptional.isPresent()) {
            return new ResponseEntity<>("LedResource not found", HttpStatus.NOT_FOUND);
        }

        if (!tagOptional.isPresent()) {
            Tag tag = new Tag();
            tag.setTagName(tagName);
            tagId = tagRepository.save(tag).getTagId();
        }else tagId = tagOptional.get().getTagId();


        // 创建 LedTag 并保存
        LedTagId ledTagId = new LedTagId();
        ledTagId.setResourceId(resourceId);
        ledTagId.setTagId(tagId);

        LedTag ledTag = new LedTag();
        ledTag.setId(ledTagId);
        ledTag.setLedResource(ledResourceOptional.get());

        ledTagRepository.save(ledTag);

        return ResponseEntity.ok("Tag added successfully");
    }
    @DeleteMapping
    public ResponseEntity<String> deleteTag(@RequestParam String resourceId, @RequestParam String tagName) {
        Optional<Tag> tagOptional = tagRepository.findByTagName(tagName);
        String tagId=tagOptional.get().getTagId();
        LedTag ledTag = ledTagRepository.findByIdResourceIdAndTagId(resourceId, tagId);
        if (ledTag == null) {
            return ResponseEntity.ok("LedTag not found");
        }
        ledTagRepository.delete(ledTag);
        return ResponseEntity.ok("Tag deleted successfully");
    }
}
