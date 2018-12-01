package com.daly_roddy.API.Development;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class myController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String landingPage(@RequestParam("name") String name){
        return name;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String page(){
        return "Welcome.html";
    }


}
