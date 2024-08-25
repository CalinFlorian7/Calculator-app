package com.example.calculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class CalculatorController {
    @GetMapping
    String getCalculator(Model model)
    {
        model.addAttribute("title","Welcome to the calculator app");
        model.addAttribute("message","Use the calculator to perform basic operations");
        return "calculator";
    }

}
