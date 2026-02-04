package com.fresh.temp.yui.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class OrgAddDto {

    @NotBlank(message = "orgName不能为空")
    private String orgName;
    @NotNull(message = "orgCommissionMoney不能为空")
    //@Digits(integer = 9, fraction = 2, message = "orgCommissionMoney out of range")
    private BigDecimal orgCommissionMoney;

}
