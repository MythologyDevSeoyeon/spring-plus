package org.example.expert.domain.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("api/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("어플리케이션이 실행 중 입니다.");
    }
}
