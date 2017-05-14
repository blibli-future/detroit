package com.blibli.future.detroit.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DetroitViewController {
    @RequestMapping("/view/**")
    public String homeView(Model model) {
        return "index";
    }
}
