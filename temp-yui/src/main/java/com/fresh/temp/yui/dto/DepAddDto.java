package com.fresh.temp.yui.dto;

import com.fresh.temp.yui.enums.DepType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DepAddDto {

    private String depName;
    @NotNull(message = "depNo不能为空")
    private Byte depNo;
    private Long orgId;
    //@NotNull(message = "未传depType参数或者值不合法")
    private DepType depType;

}
