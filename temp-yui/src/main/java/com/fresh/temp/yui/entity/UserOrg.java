package com.fresh.temp.yui.entity;

import com.fresh.xy.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserOrg extends BaseEntity<Long> {

    private long userId;
    private long orgId;
    private LocalDateTime joinTime;

}
