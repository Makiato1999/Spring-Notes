package com.easybytes.easyschool.controller;

import com.easybytes.easyschool.model.Courses;
import com.easybytes.easyschool.model.Easyclass;
import com.easybytes.easyschool.model.Person;
import com.easybytes.easyschool.repository.CoursesRespository;
import com.easybytes.easyschool.repository.EasyClassRepository;
import com.easybytes.easyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    CoursesRespository coursesRespository;

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

    @RequestMapping("/displayCourses")
    public ModelAndView displayCourses() {
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        List<Courses> coursesList = coursesRespository.findAll();
        modelAndView.addObject("courses", coursesList);
        modelAndView.addObject("course", new Courses());
        return modelAndView;
    }

    @RequestMapping(value = "/addNewCourse", method = RequestMethod.POST)
    public ModelAndView addNewCourse(@ModelAttribute Courses courses) {
        coursesRespository.save(courses);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @RequestMapping(value = "/viewStudents", method = RequestMethod.GET)
    public ModelAndView viewStudents(@RequestParam int id, HttpSession session,
                                     @RequestParam(value = "error", required = false) String error) {
        Optional<Courses> courses = coursesRespository.findById(id);
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        modelAndView.addObject("courses", courses.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("courses", courses.get());

        String errorMessage = "";
        if (error != null) {
            errorMessage = "Invalid Email entered!";
            modelAndView.addObject("errorMessage", errorMessage);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/addStudentToCourse", method = RequestMethod.POST)
    public ModelAndView addStudentToCourse(@ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) session.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()+"&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courses", courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }

    @RequestMapping(value = "/deleteStudentFromCourse", method = RequestMethod.GET)
    public ModelAndView deleteStudentFromCourse(@RequestParam int personId, HttpSession session) {
        // which course needs to be deleted
        Courses courses = (Courses) session.getAttribute("courses");
        // current student
        Optional<Person> person = personRepository.findById(personId);
        // 从该person的表里找到注册的所有课程，然后移除这节课程
        person.get().getCourses().remove(courses);
        // 从courses表里找到所有学生，然后移除这位学生
        courses.getPersons().remove(person);

        personRepository.save(person.get());
        session.setAttribute("courses", courses);

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/viewStudents?id="+courses.getCourseId());

        return modelAndView;
    }


}
