package com.fresh.temp.yui.dto;

import com.fresh.xy.common.dto.PageDto;
import com.fresh.temp.yui.enums.DepType;
import lombok.Data;

@Data
public class DepPageDto extends PageDto {
    private Long id;
    private String depName;
    private Byte depNo;
    private Long orgId;
    private DepType depType;
}
