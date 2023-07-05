package com.javainuse.controller;

import com.javainuse.request.EmployeeDto;
import com.javainuse.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public final class KafkaController {
    private final ProducerService producerService;

    @Autowired
    public KafkaController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        this.producerService.sendMessage(message);
    }

    @PostMapping(value = "/createUser")
    public void sendMessageToKafkaTopic(@RequestBody EmployeeDto request) {
        this.producerService.saveCreateUserLog(request);
    }

}