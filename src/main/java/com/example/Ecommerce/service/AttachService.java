package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.extre.AttachDTO;
import com.example.Ecommerce.entity.AttachEntity;
import com.example.Ecommerce.exp.AppBadException;
import com.example.Ecommerce.repository.AttachRepository;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Service
public class AttachService {

    @Autowired
    private AttachRepository attachRepository;
    @Value("${server.url}")
    private String serverUrl;


    public AttachDTO save(MultipartFile file) { // mazgi.png
        try {
            String pathFolder = getYMDString(); // 2022/04/23

            File folder = new File("uploads/" + pathFolder);
            if (!folder.exists()) { // uploads/2022/04/23
                folder.mkdirs();
            }

            String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
            String extension = getExtension(file.getOriginalFilename()); // mp3/jpg/npg/mp4

            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder + "/" + key + "." + extension);
            //                         uploads/2022/04/23/dasdasd-dasdasda-asdasda-asdasd.jpg
            //                         uploads/ + Path + id + extension
            Files.write(path, bytes);

            Double durationInSeconds = getDurationInSeconds(path.toAbsolutePath());
            String url=serverUrl+"/attach/open/"+key+"."+extension;

            AttachEntity entity = new AttachEntity();
            entity.setSize(file.getSize());
            entity.setExtension(extension);
            entity.setOriginalName(file.getOriginalFilename());
            entity.setCreatedData(LocalDateTime.now());
            entity.setId(key);
            entity.setPath(pathFolder);
            entity.setDuration(durationInSeconds);
            entity.setUrl(url);

            attachRepository.save(entity);

            return toDTO(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] loadImage(String attachId) { // dasdasd-dasdasda-asdasda-asdasd.jpg
        String id = attachId.substring(0, attachId.lastIndexOf("."));
        AttachEntity entity = get(id);

        byte[] data;
        try {
            Path file = Paths.get("uploads/" + entity.getPath() + "/" + attachId);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];

    }

    public byte[] loadImage2(String attachId) { // dasdasd-dasdasda-asdasda-asdasd.jpg
        String id = attachId.substring(0, attachId.lastIndexOf("."));
        AttachEntity entity = get(id);

        byte[] data;
        try {
            Path file = Paths.get("uploads/" + entity.getOriginalName() + "/" + attachId);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];

    }

   public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Image File not found");
        });
    }


    public byte[] open_general(String attachId) {
        String id = attachId.substring(0, attachId.lastIndexOf("."));
        AttachEntity entity = get(id);
        byte[] data;
        try {
            Path file = Paths.get("uploads/" + entity.getPath() + "/" + attachId);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

//    public String getYMDString() {
//        int year = Calendar.getInstance().get(Calendar.YEAR);
//        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
//        int day = Calendar.getInstance().get(Calendar.DATE);
//
//
//        return year + "/" + month + "/" + day; // 2024/4/23
//    }

    public String getYMDString() {
        LocalDate today = LocalDate.now();

        int year = today.getYear();
//        int month = today.getMonthValue();
        String  monthName = today.getMonth().toString().toLowerCase(Locale.ROOT);
        int day = today.getDayOfMonth();

        return year + "/" + monthName + "/" + day; // 2024/4/23
    }


    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setUrl(serverUrl + "/attach/open_general/" + entity.getId() + "." + entity.getExtension());
        dto.setSize(entity.getSize());
        dto.setPath(entity.getPath());
        dto.setExtension(entity.getExtension());
        dto.setCreatedData(entity.getCreatedData());
        dto.setOriginalName(entity.getOriginalName());
        dto.setDuration(entity.getDuration());
        return dto;
    }


    public Boolean delete(String attachId) {
        String id = attachId.substring(0, attachId.lastIndexOf("."));
        AttachEntity attachEntity = get(id);
        attachRepository.delete(attachEntity);
        Path path=Path.of("uploads/"+attachEntity.getPath()+"/"+attachId);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return true;
    }

    public ResponseEntity download(String attachId) {

        try {
            AttachEntity attachEntity = get(attachId);

            String id= attachId.substring(0, attachEntity.getId().lastIndexOf("."));
            AttachEntity entity = get(id);
            Path file = Paths.get("uploads/" + entity.getPath() + "/" + attachId);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOriginalName() + "\"").body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public Double getDurationInSeconds(Path videoFilePath) {
        Path absolutePath = videoFilePath.toAbsolutePath();
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(absolutePath.toString())) {
            grabber.start();
            long durationMicroseconds = grabber.getLengthInTime();
            return (double) (durationMicroseconds / 1_000_000); // Microseconds to seconds
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
            return (double) -1; // Indicate error with negative value
        }
    }

}
