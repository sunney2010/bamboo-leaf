package com.bamboo.leaf.demo.mybatis.service.impl;

import com.bamboo.leaf.demo.mybatis.Model.BambooDemoModel;
import com.bamboo.leaf.demo.mybatis.mapper.BambooDemoMapper;
import com.bamboo.leaf.demo.mybatis.service.BambooLeafDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BambooLeafDemoServiceImpl implements BambooLeafDemoService {
    @Autowired
    BambooDemoMapper bambooDemoMapper;
    @Override
    public BambooDemoModel queryBambooDemoById(Long id) {
       return bambooDemoMapper.queryBambooDemoById(id);
    }

    @Override
    public int insertBambooDemo(BambooDemoModel model) {
        return bambooDemoMapper.insertBambooDemo(model);
    }
}
