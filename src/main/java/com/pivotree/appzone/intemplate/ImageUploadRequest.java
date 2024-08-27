package com.pivotree.appzone.intemplate;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadRequest {
    private String username;
    private MultipartFile imageFile;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
