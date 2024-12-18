package org.example.service;

import org.example.enity.LedResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LedResourceService {
    public List<LedResource> init();
    public List<LedResource> orderByPlaybackVolume();
    public List<LedResource> orderByLikes();
    public LedResource getResource(String resourceId);
    public Resource getImage(String imageName) throws IOException;
    public List<LedResource> search(String userId, String name);
    public String uploadledresource(LedResource ledResource, String userId, MultipartFile fileUpload);
    public String updateResource(String resourceId,Integer playRecordNum,Integer downloadNum,Integer commentNum);
    public String deleteResource(String resourceId);
}
