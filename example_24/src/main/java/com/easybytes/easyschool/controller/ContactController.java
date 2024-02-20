package com.easybytes.easyschool.controller;

import com.easybytes.easyschool.model.Contact;
import com.easybytes.easyschool.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class ContactController {
    // 如果使用lambok，那就不需要下面这个了
    // private static Logger log = LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value={"/contact"})
    public String displayContactPage(Model model) {
        //model.addAttribute("username", "Shawn Xie");
        model.addAttribute("contact", new Contact());
        return "contact.html";
    }

//    @RequestMapping(value = "/saveMsg", method = POST)
//    //@PostMapping(value = "/saveMsg")
//    public ModelAndView saveMessage(@RequestParam String name, @RequestParam String mobileNum,
//                                    @RequestParam String email, @RequestParam String subject,
//                                    @RequestParam String message) {
//        log.info("Name : "+name);
//        log.info("mobileNum : "+mobileNum);
//        log.info("email : "+email);
//        log.info("subject : "+subject);
//        log.info("message : "+message);
//        return new ModelAndView("redirect:/contact");
//    }
    @RequestMapping(value = "/saveMsg", method = POST)
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Contact from validation failed due to : "+errors.toString());
            return "contact";
        } // catch the errors and display to end user所以我们需要去修改contact的前端
        contactService.saveMessageDetails(contact);
        return "redirect:/contact";
    }
}
