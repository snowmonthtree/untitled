package org.example.service;

import org.example.entity.LedResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LedResourceService {
    List<LedResource> init();
    List<LedResource> orderByPlaybackVolume();
    List<LedResource> orderByLikes();
    LedResource getResource(String resourceId);
    Resource getImage(String imageName) throws IOException;
    List<LedResource> search(String userId, String name);
    String uploadledresource(LedResource ledResource, String userId, MultipartFile fileUpload);
    String updateResource(String resourceId,Integer playRecordNum,Integer downloadNum,Integer commentNum);
    String deleteResource(String resourceId);
}
