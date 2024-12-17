package org.example.controller;

import org.example.enity.LedResource;
import org.example.enity.User;
import org.example.repository.LedResourceRepository;
import org.example.repository.UserRepository;
import org.example.enity.Likes;
import org.example.repository.LikesRepository;
import org.example.enity.PlayRecord;
import org.example.repository.PlayRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/led-resources")
public class LedResourceController {
    private static final String IMAGE_DIRECTORY = "C:\\Users\\Administrator\\Pictures";  // 图片存储目录

    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private PlayRecordRepository playRecordRepository;
    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/init")
    public List<LedResource> init(){
        return ledResourceRepository.findByOrderByUpTimeDesc();
    }
    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        // 根据图片名称构造文件路径
        Path imagePath = Paths.get(IMAGE_DIRECTORY , imageName);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            // 返回图片内容
            return ResponseEntity.ok().body(resource);
        } else {
            // 如果图片不存在或不可读，返回 404 错误
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/search")
    public List<LedResource> search(@RequestParam String userId,@RequestParam String name){
        return ledResourceRepository.findByUser_UserIdContainingOrNameContaining(userId,name);
    }

    @GetMapping("/order-by-playback-volume")
    public List<LedResource> orderByPlaybackVolume() {
        return ledResourceRepository.findByOrderByPlaybackVolumeDesc();
    }

    @GetMapping("/order-by-likes")
    public List<LedResource> orderByLikes() {
        return ledResourceRepository.findByOrderByLikesDesc();
    }

    @GetMapping("/getresource/{resourceId}")
    public LedResource getResource(@PathVariable String resourceId){
        return ledResourceRepository.findByResourceId(resourceId);
    }
    @PostMapping("uploadresource")
    public String uploadledresource(@RequestPart("ledresource") LedResource ledResource,@RequestParam String userId, @RequestParam("file") MultipartFile fileUpload) {
        if (fileUpload.isEmpty()) {
            return "Failed to upload file: File is empty";
        }

        File tmp = new File(IMAGE_DIRECTORY);
        if (!tmp.exists()) {
            tmp.mkdirs();
        }
        User user=userRepository.findByUserId(userId);
        ledResource.setUser(user);

        String originalFileName = fileUpload.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        // 构建完整的文件路径
        String fileName = ledResource.getResourceId() + fileExtension;
        String newFilePath = IMAGE_DIRECTORY + File.separator + fileName;

        LedResource oldResource = ledResourceRepository.findByResourceId(ledResource.getResourceId());

        File upFile = new File(newFilePath);
        try {
            fileUpload.transferTo(upFile);
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }

        ledResource.setResourceWebUrl(newFilePath);
        ledResource.setViewWebUrl(newFilePath);

        if (oldResource != null) {
            // 更新已存在的资源
            String oldFilePath = oldResource.getResourceWebUrl();
            if (oldFilePath != null) {
                File oldFile = new File(oldFilePath);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
        }

        ledResourceRepository.save(ledResource);
        return "File uploaded successfully";
    }
    @PostMapping("update")
    public String updateResource(@RequestPart("ledresource") LedResource ledResource){
        ledResourceRepository.save(ledResource);
        return "success";
    }

}
