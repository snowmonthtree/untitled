package org.example.service;

import org.example.entity.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class LedResourceServiceImpl implements LedResourceService{
    private static final String IMAGE_DIRECTORY = "C:\\Users\\Administrator\\Pictures";  // 图片存储目录

    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private PlayRecordRepository playRecordRepository;
    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UploadRecordRepository uploadRecordRepository;
    @Autowired
    private LedListRepository ledListRepository;
    @Autowired
    private LedTagRepository ledTagRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AuditRepository auditRepository;
    @Autowired
    private LedListService ledListService;


    @Override
    public List<LedResource> init(){
        return ledResourceRepository.findByOrderByUpTimeDesc();
    }
    @Override
    public Resource getImage(String imageName) throws IOException {
        // 根据图片名称构造文件路径
        Path imagePath = Paths.get(IMAGE_DIRECTORY , imageName);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            // 返回图片内容
            return resource;
        }else {
            return null;
        }
    }
    @Override
    public List<LedResource> search(String userId, String name){
        return ledResourceRepository.findByUser_UserIdContainingOrNameContaining(userId,name);
    }

    @Override
    public List<LedResource> orderByPlaybackVolume() {
        return ledResourceRepository.findByOrderByPlaybackVolumeDesc();
    }

    @Override
    public List<LedResource> orderByLikes() {
        return ledResourceRepository.findByOrderByLikesDesc();
    }

    @Override
    public LedResource getResource(String resourceId){
        return ledResourceRepository.findByResourceId(resourceId);
    }

    @Override
    public String uploadledresource(LedResource ledResource, String userId, MultipartFile fileUpload) {
        if (fileUpload.isEmpty()) {
            return "Failed to upload file: File is empty";
        }

        File tmp = new File(IMAGE_DIRECTORY);
        if (!tmp.exists()) {
            tmp.mkdirs();
        }
        User user=userRepository.findByUserId(userId);
        ledResource.setUser(user);
        // 使用UUID生成唯一的文件名，并保留文件扩展名
        String originalFileName = fileUpload.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;
        String newFilePath = IMAGE_DIRECTORY + File.separator + fileName;

        LedResource oldResource = ledResourceRepository.findByResourceId(ledResource.getResourceId());

        File upFile = new File(newFilePath);
        try {
            fileUpload.transferTo(upFile);
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }

        ledResource.setResourceWebUrl(fileName);
        ledResource.setViewWebUrl(fileName);

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
        UploadRecord uploadRecord=new UploadRecord();
        uploadRecord.setUser(user);
        uploadRecord.setResource(ledResource);
        uploadRecord.setUploadTime(new Timestamp(System.currentTimeMillis()));
        uploadRecordRepository.save(uploadRecord);
        return "File uploaded successfully";
    }

    @Override
    public String updateResource(String resourceId,Integer playRecordNum,Integer downloadNum,Integer commentNum){
        LedResource ledResource=ledResourceRepository.findByResourceId(resourceId);
        ledResource.setPlaybackVolume(playRecordNum);
        ledResource.setDownloadCount(downloadNum);
        ledResource.setCommentNum(commentNum);
        ledResourceRepository.save(ledResource);
        return "Resource update successfully";
    }

    @Override
    public String deleteResource(String userId,String resourceId){
        LedResource ledResource = ledResourceRepository.findByResourceId(resourceId);
        User user = userRepository.findByUserId(userId);
        if (ledResource != null) {
            List<PlayRecord> playRecords = playRecordRepository.findByIdResourceId(resourceId);
            List<Likes> likes = likesRepository.findByLedResource_ResourceId(resourceId);
            List<Comment> comments = commentRepository.findByLedResource_ResourceId(resourceId);
            List<LedList> ledLists = ledListRepository.findByIdResourceId(resourceId);
            List<LedTag> ledTags = ledTagRepository.findByIdResourceId(resourceId);
            List<UploadRecord> uploadRecords = uploadRecordRepository.findByResource_ResourceId(resourceId);
            Audit audit = auditRepository.findByResource_ResourceId(resourceId);
            if (playRecords != null) {
                playRecordRepository.deleteAll(playRecords);
            }
            if (uploadRecords != null) {
                uploadRecordRepository.deleteAll(uploadRecords);
            }
            if (likes != null) {
                likesRepository.deleteAll(likes);
            }
            if (comments != null) {
                commentRepository.deleteAll(comments);
            }
            if (ledTags != null) {
                ledTagRepository.deleteAll(ledTags);
            }
            if (audit != null) {
                auditRepository.delete(audit);
            }
            if (ledLists != null) {
                for (LedList ledList : ledLists) {
                    ledListService.removeResourceFromPlaylist(user.getUserId(), ledList.getId().getPlaylistId(), resourceId);
                }
            }
            Path imagePath = Paths.get(IMAGE_DIRECTORY, ledResource.getResourceWebUrl());
            if (imagePath.toFile().exists()) {
                try {
                    imagePath.toFile().delete();
                } catch (Exception e) {
                    return "Failed to delete file: " + e.getMessage();
                }
            }
            ledResourceRepository.delete(ledResource);
        } else {
            return "Resource not found";
        }
        return "Delete resource success";
    }

}

