package com.audiophile.streamify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/listener")
@RequiredArgsConstructor
public class ListenerController {
    @GetMapping()
    public ResponseEntity<String> greeting(){
        return ResponseEntity.ok("Hi Listener");
    }
}
