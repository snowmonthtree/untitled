package org.example.controller;

import org.example.enity.LedResource;
import org.example.repository.LedResourceRepository;
import org.example.enity.Likes;
import org.example.repository.LikesRepository;
import org.example.enity.PlayRecord;
import org.example.repository.PlayRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

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

    @GetMapping("/init")
    public List<LedResource> init(){
        return ledResourceRepository.findTop8ByOrderByUpTimeDesc();
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

    //获取播放量
    @GetMapping("/playback-volume")
    public ResponseEntity<Integer> getPlaybackVolume(@RequestParam String resourceId) {
        long count = playRecordRepository.countByResourceId(resourceId);
        LedResource ledResource = ledResourceRepository.findByResourceId(resourceId);
        if (ledResource != null) {
            ledResource.setPlaybackVolume((int) count);
            ledResourceRepository.save(ledResource); // 保存更新后的实体
        }
        return ResponseEntity.ok((int) count);
    }
    @GetMapping("/order-by-playback-volume")
    public List<LedResource> orderByPlaybackVolume() {
        return ledResourceRepository.findByOrderByPlaybackVolumeDesc();
    }

    //获取点赞量
    @GetMapping("/likesnum")
    public ResponseEntity<Integer> getLikesNum(@RequestParam String resourceId) {
        long count = likesRepository.countByResourceId(resourceId);
        LedResource ledResource = ledResourceRepository.findByResourceId(resourceId);
        if (ledResource != null) {
            ledResource.setLikes((int) count);
            ledResourceRepository.save(ledResource); // 保存更新后的实体
        }
        return ResponseEntity.ok((int) count);
    }
    @GetMapping("/order-by-likes")
    public List<LedResource> orderByLikes() {
        return ledResourceRepository.findByOrderByLikesDesc();
    }
}
