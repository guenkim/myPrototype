package com.guen.program.jpashop.controller;


import com.guen.program.jpashop.domain.Address;
import com.guen.program.jpashop.domain.Crew;
import com.guen.program.jpashop.service.CrewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class CrewController {

    private final CrewService crewService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Crew crew = new Crew();
        crew.setName(form.getName());
        crew.setAddress(address);

        crewService.join(crew);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Crew> crew = crewService.findMembers();
        model.addAttribute("members", crew);
        return "members/memberList";
    }

}
