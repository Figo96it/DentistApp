package com.damian.figinski.dentistapp.controller.dentist;

import com.damian.figinski.dentistapp.model.User;
import com.damian.figinski.dentistapp.service.DentistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/dentists")
public class DentistController {

    private final DentistService dentistService;

    public DentistController(DentistService dentistService) {
        this.dentistService = dentistService;
    }

    @GetMapping
    public String listDentists(Model model) {
        List<User> dentists = dentistService.findAllDentists();
        model.addAttribute("dentists", dentists);
        return "admin/dentists";
    }
}
