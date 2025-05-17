package com.lec.spring.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefundForm {
    @NotNull(message = "환급 금액을 입력해주세요.")
    @Min(value = 10000, message = "최소 환급 금액은 10,000원부터 가능합니다.")
    private Integer amount;

    @NotBlank(message = "은행명을 입력해주세요.")
    private String bankName;

    @NotBlank(message = "예금주를 입력해주세요.")
    private String holder;

    @NotBlank(message = "계좌번호를 입력해주세요.")
    private String accountNumber;
}
