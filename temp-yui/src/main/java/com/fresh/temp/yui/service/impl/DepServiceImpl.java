package com.fresh.temp.yui.service.impl;


import com.fresh.xy.mb.core.BaseServiceImpl;
import com.fresh.temp.yui.entity.Dep;
import com.fresh.temp.yui.mapper.DepMapper;
import com.fresh.temp.yui.service.DepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepServiceImpl extends BaseServiceImpl<Long, Dep, DepMapper> implements DepService {

    @Autowired
    private DepMapper depMapper;

}
