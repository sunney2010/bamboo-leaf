package com.bamboo.leaf.core.entity;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/10 下午9:56
 */
public class DemoDO {

    private Integer id;
    private String name;
    private Integer age;
    private Long num;
    /**
     * increment by
     */
    private Integer delta;
    /**
     * mod num
     */
    private Integer remainder;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getNum() {
        return num;
    }

    public Integer getDelta() {
        return delta;
    }

    public void setDelta(Integer delta) {
        this.delta = delta;
    }

    public Integer getRemainder() {
        return remainder;
    }

    public void setRemainder(Integer remainder) {
        this.remainder = remainder;
    }

    public void setNum(Long num) {
        this.num = num;
    }
}
