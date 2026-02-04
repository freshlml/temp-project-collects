package com.fresh.temp.yui.entity;

import com.fresh.xy.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseEntity<Long> {

    private String mobile;
    private String nickName;

}
