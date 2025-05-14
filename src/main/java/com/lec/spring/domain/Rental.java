package com.lec.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {

    private Long id;
    private LocalDateTime rented_at;
    private LocalDateTime retrurn_due_at;
    private LocalDateTime retrurned_at;
    private String status;

    private User user;
    private Item item;
    private String itemName;  // item 테이블에서 join으로 받아올 상품명

}
