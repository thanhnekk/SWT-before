package com.learning.codelearn.uploadingfiles.storage;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {
    private String location = "C:\\Users\\Admin\\Downloads\\demo\\demo\\path";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
