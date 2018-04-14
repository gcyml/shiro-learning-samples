package com.boot.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boot.domain.UserInfo;

/**
 * @author wgc
 */
@Controller
public class MainController {

    @RequestMapping({"/","/index"})
    public String index(Model model) {
    	UserInfo user = (UserInfo)SecurityUtils.getSubject().getPrincipal();
    	model.addAttribute("user", user);
        return "index";

    }

    /**
	 * 若表单认证失败则进入到此方法中
	 */
	@RequestMapping("/login")
    public String loginForm() {
	    return "login";
    }

}