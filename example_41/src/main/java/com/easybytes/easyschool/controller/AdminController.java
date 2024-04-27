package com.easybytes.easyschool.controller;

import com.easybytes.easyschool.model.Easyclass;
import com.easybytes.easyschool.model.Person;
import com.easybytes.easyschool.repository.EasyClassRepository;
import com.easybytes.easyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    EasyClassRepository easyClassRepository;

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model) {
        List<Easyclass> easyclassList = easyClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("easyClasses", easyclassList);
        modelAndView.addObject("easyClass", new Easyclass());
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model, @ModelAttribute("easyClass") Easyclass easyclass) {
        easyClassRepository.save(easyclass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id) {
        Optional<Easyclass> easyclass = easyClassRepository.findById(id);
        for (Person person : easyclass.get().getPersons()) {
            person.setEasyClass(null);
            personRepository.save(person);
        }
        easyClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<Easyclass> easyclass = easyClassRepository.findById(classId);
        modelAndView.addObject("easyClass", easyclass.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("easyClass", easyclass.get());
        // 使用session：将easyclass.get()对象存储到HttpSession中，允许在多个请求中重复使用这个对象，而不必每次都查询数据库。

        String errorMessage = "";
        if (error != null) {
            errorMessage = "Invalid Email entered!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @RequestMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Easyclass easyclass = (Easyclass) session.getAttribute("easyClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+easyclass.getClassId()+"&error=true");
            return modelAndView;
        }
        personEntity.setEasyClass(easyclass);
        personRepository.save(personEntity);
        easyclass.getPersons().add(personEntity);
        easyClassRepository.save(easyclass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+easyclass.getClassId());
        return modelAndView;
    }

    @RequestMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        Easyclass easyclass = (Easyclass) session.getAttribute("easyClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setEasyClass(null);
        easyclass.getPersons().remove(person.get());
        easyClassRepository.save(easyclass);
        session.setAttribute("easyClass", easyclass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+easyclass.getClassId());
        return modelAndView;
    }
}
