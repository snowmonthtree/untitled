package org.example.controller;

import org.example.entity.LedResource;
import org.example.service.LedResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/led-resources")
public class LedResourceController {
    private static final String IMAGE_DIRECTORY = "C:\\Users\\Administrator\\Pictures";  // 图片存储目录
    @Autowired
    private LedResourceService ledResourceService;

    @GetMapping("/init")
    public List<LedResource> init(){
        return ledResourceService.init();
    }
    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName)  {
        try {
            Resource resource=ledResourceService.getImage(imageName);
            return ResponseEntity.ok().body(resource);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/search")
    public List<LedResource> search(@RequestParam String userId,@RequestParam String name){
        return ledResourceService.search(userId,name);
    }

    @GetMapping("/order-by-playback-volume")
    public List<LedResource> orderByPlaybackVolume() {
        return ledResourceService.orderByPlaybackVolume();
    }

    @GetMapping("/order-by-likes")
    public List<LedResource> orderByLikes() {
        return ledResourceService.orderByLikes();
    }

    @GetMapping("/getresource/{resourceId}")
    public LedResource getResource(@PathVariable String resourceId){
        return ledResourceService.getResource(resourceId);
    }
    @PostMapping("uploadresource")
    public String uploadledresource(@RequestPart("ledresource") LedResource ledResource,@RequestParam String userId, @RequestParam("file") MultipartFile fileUpload) {
        try {
            return ledResourceService.uploadledresource(ledResource,userId,fileUpload);
        }
        catch (Exception e) {
            return "error";
        }
    }
    @PostMapping("update")
    public String updateResource(@RequestParam String resourceId,@RequestParam Integer playRecordNum,@RequestParam Integer downloadNum,@RequestParam Integer commentNum){
        return ledResourceService.updateResource(resourceId,playRecordNum,downloadNum,commentNum);
    }
    @PostMapping("delete")
    public String deleteResource(@RequestParam String resourceId){
        return ledResourceService.deleteResource(resourceId);
    }

}
