package com.cy.store.controller.other;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserSignOutController {
    @RequestMapping("/signOut")
    public String signOut(HttpSession session){
        session.removeAttribute("uid");
        return "redirect:/web/login.html";
    }
}
