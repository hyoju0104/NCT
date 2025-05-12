package com.lec.spring.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    private Long id;
    private String name;
    private String category;
    private String description;
    private boolean is_available;
    private LocalDateTime createdAt;

    public enum ItemStatus{A, B, C};
    private ItemStatus statusItem;

    private String image_sourcename;
    private String image_filename;

    private Brand brand;

    /*
    @ToString.Exclude
    @Builder.Default
    private List<Attachment> fileList = new ArrayList<>();
    */
}
