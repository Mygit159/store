package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

/**
 * 地区业务
 */
public interface IDistrictService {
    List<District> findByParent(String parent);
}
