package com.example.Ecommerce.controller.thymeleaf;

import com.example.Ecommerce.dto.AttributeDTO;
import com.example.Ecommerce.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/attribute")
public class Attribute {

    @Autowired
    private AttributeService attributeService;

        @GetMapping("/go-to-create")
        public String showCreateForm(Model model) {
            model.addAttribute("attributeDTO", new AttributeDTO());
            return "attribute/create-attribute";
        }

        @PostMapping("/create")
        public String create(@ModelAttribute AttributeDTO attributeDTO) {
            attributeService.create(attributeDTO);
            return "redirect:/attribute/list";
        }

    @GetMapping("/list")
    public String list(Model model) {
        List<AttributeDTO> list = attributeService.getList();
        model.addAttribute("attributeList",list);
        return "attribute/attribute-list";
    }



}
