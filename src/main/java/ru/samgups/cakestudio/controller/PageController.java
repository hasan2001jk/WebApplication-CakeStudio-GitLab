package ru.samgups.cakestudio.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.samgups.cakestudio.repository.UserRepository;


import javax.servlet.http.HttpServletRequest;

@Controller
public class PageController {

    @Autowired
    ProductController productController;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        System.out.println(authentication);
        System.out.println(authentication.getAuthorities());
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/news")
    public String news(Model model, HttpServletRequest request){
        return productController.getPage(model,request);
    }

    @GetMapping("/o_nas")
    public String o_nas(Model model,HttpServletRequest request){
        return productController.getPage(model,request);
    }


    @GetMapping("/contacts")
    public String contacts(Model model,HttpServletRequest request){
        return productController.getPage(model,request);
    }


    @GetMapping("/credentials")
    public String credentials(Model model,HttpServletRequest request){
        return productController.getPage(model,request);
    }

    @GetMapping("/produce")
    public String produce(Model model,HttpServletRequest request){
        return productController.getPage(model,request);
    }

    @GetMapping("/delivery_and_pay")
    public String delivery_and_pay(Model model,HttpServletRequest request){
        return productController.getPage(model,request);
    }

    @GetMapping("/licenses_detail")
    public String licenses_detail(Model model,HttpServletRequest request){
        return productController.getPage(model,request);
    }


    @GetMapping("/payment_conditions")
    public String payment_conditions(Model model,HttpServletRequest request){
        return productController.getPage(model,request);
    }

    @GetMapping("/forgot_password")
    public String forgotPassword(Model model,HttpServletRequest request){
        return productController.getPage(model,request);
    }


}
