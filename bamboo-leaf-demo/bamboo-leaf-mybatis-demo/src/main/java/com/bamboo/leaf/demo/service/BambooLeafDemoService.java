package com.bamboo.leaf.demo.service;

import com.bamboo.leaf.demo.Model.BambooDemoModel;

public interface BambooLeafDemoService {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public BambooDemoModel queryBambooDemoById(Long id);


    /**
     * 新增【请填写功能名称】
     *
     * @param model 【请填写功能名称】
     * @return 结果
     */
    public int insertBambooDemo(BambooDemoModel model);
}
