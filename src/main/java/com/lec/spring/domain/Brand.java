package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {
    private Long id;
    private Long authId;
    private String name;

    private String username;
    @JsonIgnore // Json 으로 변환될 때 무시
    private String password;
    @ToString.Exclude   // toString() 메소드 생성 시 제외
    @JsonIgnore
    private String rePassword;

    private String phoneNum;
    private String logoSourcename;
    private String logoFilename;
    private String description;
    private Boolean isActived;
}
