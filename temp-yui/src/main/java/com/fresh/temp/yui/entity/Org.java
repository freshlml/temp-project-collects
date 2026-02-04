package com.fresh.temp.yui.entity;

import com.fresh.xy.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Org extends BaseEntity<Long> {

    private String orgName;
    private BigDecimal orgCommissionMoney;

}
