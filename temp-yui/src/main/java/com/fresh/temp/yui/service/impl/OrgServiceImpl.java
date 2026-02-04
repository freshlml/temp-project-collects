package com.fresh.temp.yui.service.impl;


import com.fresh.xy.mb.core.BaseServiceImpl;
import com.fresh.temp.yui.entity.Org;
import com.fresh.temp.yui.mapper.DepMapper;
import com.fresh.temp.yui.mapper.OrgMapper;
import com.fresh.temp.yui.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrgServiceImpl extends BaseServiceImpl<Long, Org, OrgMapper> implements OrgService<Long, Org> {

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private DepMapper depMapper;

    @Transactional
    @Override
    public int deleteOrg(Long id) {
        int ret = orgMapper.deleteById(id);
        depMapper.deleteByOrgId(id);

        return ret;
    }
}
