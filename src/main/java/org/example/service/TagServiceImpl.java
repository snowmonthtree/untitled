package org.example.service;

import org.example.entity.LedResource;
import org.example.entity.LedTag;
import org.example.entity.LedTagId;
import org.example.entity.Tag;
import org.example.repository.LedResourceRepository;
import org.example.repository.LedTagRepository;
import org.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private LedTagRepository ledTagRepository;

    @Override
    public String addTag(String resourceId, String tagName) {
        // 查找 LedResource
        Optional<LedResource> ledResourceOptional = ledResourceRepository.findById(resourceId);
        Optional<Tag> tagOptional = tagRepository.findByTagName(tagName);
        String tagId;
        if (ledResourceOptional.isEmpty()) {
            return "LedResource not found";
        }

        if (tagOptional.isEmpty()) {
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
        return "Tag added successfully";
    }
    @Override
    public String deleteTag(String resourceId, String tagName) {
        Optional<Tag> tagOptional = tagRepository.findByTagName(tagName);
        if (tagOptional.isEmpty()) {
            return "Tag not found";
        }
        String tagId=tagOptional.get().getTagId();
        LedTag ledTag = ledTagRepository.findByIdResourceIdAndTagId(resourceId, tagId);
        if (ledTag == null) {
            return "LedTag not found";
        }
        ledTagRepository.delete(ledTag);
        return "Tag deleted successfully";
    }
}
