package com.fresh.temp.yui.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class OrgUpdateDto {

    @NotNull(message = "id不能为空")
    private Long id;
    private String orgName;
    private BigDecimal orgCommissionMoney;


}
