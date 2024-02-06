package com.guen.program.shop;

import com.guen.jwt.security.UserAuthorize;
import com.guen.program.shop.model.dto.TestReq;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@UserAuthorize
@RequestMapping("/api2")
public class AllInOne {
    @PostMapping("/test")
    public ResponseEntity test(
            @RequestBody @Valid TestReq testReq
    ){
        return ResponseEntity.noContent().build();
    }
}
