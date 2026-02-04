package com.fresh.temp.yui.service;

import com.fresh.xy.common.entity.BaseEntity;
import com.fresh.xy.mb.core.BaseService;

public interface OrgService<ID, E extends BaseEntity<ID>> extends BaseService<ID, E> {

    int deleteOrg(ID id);

}
