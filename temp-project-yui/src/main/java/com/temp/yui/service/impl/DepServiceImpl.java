package com.temp.yui.service.impl;


import com.fresh.xy.mb.core.BaseServiceImpl;
import com.temp.yui.entity.Dep;
import com.temp.yui.mapper.DepMapper;
import com.temp.yui.service.DepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepServiceImpl extends BaseServiceImpl<Long, Dep, DepMapper> implements DepService {

    @Autowired
    private DepMapper depMapper;

}
