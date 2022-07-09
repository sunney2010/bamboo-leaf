package com.bamboo.leaf.demo.mapper;

import com.bamboo.leaf.demo.Model.BambooDemoModel;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2020-12-17
 */
public interface BambooDemoMapper {
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
