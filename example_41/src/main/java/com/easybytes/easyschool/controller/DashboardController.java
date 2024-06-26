package com.easybytes.easyschool.controller;

import com.easybytes.easyschool.model.Person;
import com.easybytes.easyschool.model.Profile;
import com.easybytes.easyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class DashboardController {
    @Autowired
    PersonRepository personRepository;

    @RequestMapping(value = "/dashboard", method = {RequestMethod.GET, RequestMethod.POST})
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        String email = authentication.getName();
        Person person = personRepository.readByEmail(email);
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        session.setAttribute("loggedInPerson", person);

        return "dashboard.html";
    }
}
