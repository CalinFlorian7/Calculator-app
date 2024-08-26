package com.example.calculator.controller;

import com.example.calculator.classes.Calculator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CalculatorController {
    @GetMapping("/")
    String getCalculator(Model model)
    {
        model.addAttribute("title","Welcome to the calculator app");
        model.addAttribute("message","Use the calculator to perform basic operations");
        model.addAttribute("calculator",new Calculator());
        return "calculator";
    }
    @PostMapping("/calculate")
    public String calculateOperation(@ModelAttribute Calculator calculator,Model model) {
        calculator.calculate();
        model.addAttribute("calculator",calculator);
        return "calculator";
    }

}
