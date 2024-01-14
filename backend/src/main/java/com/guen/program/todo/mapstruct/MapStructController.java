package com.guen.program.todo.mapstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapStructController {

    @GetMapping("/api/mapstruct")
    public ResponseEntity getInfo(@RequestBody OrderData orderData){
        return ResponseEntity.ok().body(orderData);
    }

}
