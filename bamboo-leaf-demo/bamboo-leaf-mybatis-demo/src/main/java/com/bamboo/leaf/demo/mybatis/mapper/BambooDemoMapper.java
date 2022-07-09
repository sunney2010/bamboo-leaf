package com.bamboo.leaf.demo.mybatis.mapper;

import com.bamboo.leaf.demo.mybatis.Model.BambooDemoModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2020-12-17
 */
@Mapper
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
