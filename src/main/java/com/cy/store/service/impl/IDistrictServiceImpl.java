package com.cy.store.service.impl;

import com.cy.store.entity.District;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.IDistrictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class IDistrictServiceImpl implements IDistrictService {
    @Resource
    private DistrictMapper districtMapper;
    @Override
    public List<District> findByParent(String parent) {
        List<District> list = districtMapper.findDistrictByParent(parent);
        /*
        * 在进行网络数据传输时，为了尽量避免无效数据的传递，可以将无效数据设置为null
        * 可以节省流量，另一方面可以提升效率
        * */
        for (District district:list){
            district.setId(null);
            district.setParent(null);
        }
        return list;
    }
}
