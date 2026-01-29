package com.temp.yui.mapper;

import com.fresh.xy.mb.core.BaseMapper;
import com.temp.yui.entity.Dep;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepMapper extends BaseMapper<Long, Dep> {

    void deleteByOrgId(Long orgId);

}
