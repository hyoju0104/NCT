package com.lec.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {
    private Long id;
    private String name;
    private String phone_num;
    private String logo_sourcename;
    private String logo_filename;
    private String description;
}
