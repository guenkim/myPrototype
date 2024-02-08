package com.guen.program.jpashop.controller;


import com.guen.jwt.security.UserAuthorize;
import com.guen.program.jpashop.model.dto.MemberForm;
import com.guen.program.jpashop.model.entity.Address;
import com.guen.program.jpashop.model.entity.Crew;
import com.guen.program.jpashop.service.CrewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Crew API")
@Slf4j
@RestController
@RequestMapping("/api")
@UserAuthorize
@RequiredArgsConstructor
public class CrewController {

    private final CrewService crewService;

    @PostMapping("/members/new")
    public ResponseEntity create(@RequestBody @Valid final MemberForm form) {
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Crew crew = new Crew();
        crew.setName(form.getName());
        crew.setAddress(address);
        crewService.join(crew);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/members")
    public ResponseEntity list() {
        List<Crew> crew = crewService.findMembers();
        return ResponseEntity.ok().body(crew);
    }

}
