package com.alu.omc;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class Loginredirect
{
    @RequestMapping(value = "/rest/login", method = RequestMethod.GET)
    public void method(HttpServletResponse response) {
        response.setHeader("Location", "https://www.163.com");
        try
        {
            response.sendRedirect("http://www.163.com");
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value = "login2", method = RequestMethod.GET)
    public String login2(HttpServletResponse request, HttpServletResponse response) {
       return "redirect:http://www.163.com";
    }

}
