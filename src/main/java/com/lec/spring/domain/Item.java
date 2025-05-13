package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    private Long id;
    private String name;
    private String category;
    private String description;
    private Boolean isAvailable;
    
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private StatusItem statusItem;
    public StatusItem getStatusItem() {
        return statusItem;
    }

    public void setStatusItem(StatusItem statusItem) {
        this.statusItem = statusItem;
    }
    public enum StatusItem{A, B, C};

    private Boolean isExist;

    private String imageSourcename;
    private String imageFilename;

    private Brand brand;

    /*
    @ToString.Exclude
    @Builder.Default
    private List<Attachment> fileList = new ArrayList<>();
    */
}
