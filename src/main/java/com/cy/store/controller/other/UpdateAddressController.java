package com.cy.store.controller.other;

import com.cy.store.controller.BaseController;
import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class UpdateAddressController extends BaseController {
    @Autowired
    private IAddressService addressService;

    @GetMapping("/update/{aid}")
    public String updateAddress(@PathVariable("aid") Integer aid, HttpSession session, Model model){
        Integer uid = getUidFromSession(session);
        Address address = addressService.getByAid(aid, uid);
        model.addAttribute("address",address);
        return "updateAddress";
    }
}
