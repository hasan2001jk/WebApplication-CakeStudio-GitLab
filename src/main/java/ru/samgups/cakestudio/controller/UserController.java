package ru.samgups.cakestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.samgups.cakestudio.dto.LoginDto;
import ru.samgups.cakestudio.dto.UserDto;
import ru.samgups.cakestudio.entity.User;
import ru.samgups.cakestudio.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDto") UserDto userDto, HttpServletRequest request) {
        // Validate userDto fields and perform necessary checks
        System.out.println(request.getParameterNames());
        Enumeration<String> inputNames=request.getParameterNames();
        while (inputNames.hasMoreElements()) {
            String inputName = inputNames.nextElement();
            System.out.println(inputName);
            System.out.println(request.getParameter(inputName));
            System.out.println("------------------------------");
        }

        userDto.setFio(request.getParameter("REGISTER[NAME]"));
        userDto.setEmail(request.getParameter("REGISTER[EMAIL]"));
        userDto.setPhone(request.getParameter("REGISTER[PERSONAL_PHONE]"));
        userDto.setId(Long.parseLong(request.getParameter("REGISTER[LOGIN]")));
        userDto.setPassword(request.getParameter("REGISTER[PASSWORD]"));

        User registeredUser = userService.registerUser(userDto);

        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("loginDto") LoginDto loginDto) {
        // Validate loginDto fields and perform necessary checks
        System.out.println(loginDto.getEmail());
        System.out.println(loginDto.getPassword());
        User loggedInUser = userService.loginUser(loginDto);
        ModelAndView modelAndView = new ModelAndView("auth");
        if(loggedInUser == null){
            modelAndView.addObject("reply_for_auth", "sp");
            return "redirect:/login";
        }else{
            return "redirect:/";
        }

    }

    // Other methods
}