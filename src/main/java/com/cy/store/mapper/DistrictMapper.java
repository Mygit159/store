package com.cy.store.mapper;

import com.cy.store.entity.District;

import java.util.List;

/**
 * 地区信息
 */
public interface DistrictMapper {
    /**
     * 通过父代号查询地区信息
     * @param parent 父代号
     * @return 返回某个父区域下的所有区域列表
     */
    List<District> findDistrictByParent(String parent);

    /**
     * 根据省市区的code获取名称
     * @param code 省市区的code代号
     * @return  返回一个省市区的地址
     */
    String findDistrictByCode(String code);
}
