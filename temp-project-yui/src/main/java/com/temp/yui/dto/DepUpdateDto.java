package com.temp.yui.dto;

import com.temp.yui.enums.DepType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class DepUpdateDto {

    @NotNull(message = "id不能为空")
    private Long id;
    private String depName;
    private Byte depNo;
    private Long orgId;
    private DepType depType;

}
