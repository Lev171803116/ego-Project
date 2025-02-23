package com.ego.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("index")
    public String welcome(){
        return "forward:showBigPic";
    }

    @RequestMapping("{page}")
    public String showPage(@PathVariable String page){
        return page;
    }
}
