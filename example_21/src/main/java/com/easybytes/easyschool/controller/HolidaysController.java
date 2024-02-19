package com.easybytes.easyschool.controller;

import com.easybytes.easyschool.model.Holiday;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HolidaysController {
    @RequestMapping(value = "/holidays")
    public String displayHolidays(Model model) {
        List<Holiday> holidays = Arrays.asList(
                new Holiday(" Jan 1 ","New Year's Day", Holiday.Type.FESTIVAL),
                new Holiday(" Oct 31 ","Halloween", Holiday.Type.FESTIVAL),
                new Holiday(" Nov 24 ","Thanksgiving Day", Holiday.Type.FESTIVAL),
                new Holiday(" Dec 25 ","Christmas", Holiday.Type.FESTIVAL),
                new Holiday(" Jan 17 ","Martin Luther King Jr. Day", Holiday.Type.FEDERAL),
                new Holiday(" July 4 ","Independence Day", Holiday.Type.FEDERAL),
                new Holiday(" Sep 5 ","Labor Day", Holiday.Type.FEDERAL),
                new Holiday(" Nov 11 ","Veterans Day", Holiday.Type.FEDERAL)
        );
        Holiday.Type[] types = Holiday.Type.values();//找到所有枚举值，FESTIVAL和FEDERAL
        for (Holiday.Type type : types) {//foreach结构，“类型 变量 ： 所有枚举值“
            //第一次循环时，type会是FESTIVAL。循环会找到所有类型为FESTIVAL的假期，并将它们作为一个列表添加到模型中，属性名为"FESTIVAL"。
            //第二次循环时，type会是FEDERAL。循环会找到所有类型为FEDERAL的假期，并将它们作为一个列表添加到模型中，属性名为"FEDERAL"。
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(
                            holiday -> holiday.getType().equals(type)).collect(
                                    Collectors.toList()
                    )));
        }
        return "holidays.html";
    }
}
