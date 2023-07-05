package com.javainuse.service;

import com.javainuse.request.EmployeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public final class ConsumerService {
	@KafkaListener(topics = "${general.topic.name}", id = "${general.topic.group.id}")
	public void consume(String message) {
		System.out.println("Message recieved "+ message);
	}

	@KafkaListener(topics = "${user.topic.name}", id = "${user.topic.group.id}", containerFactory = "userKafkaListenerContainerFactory")
	public void consume(EmployeeDto request) {
		System.out.println("Message recieved "+ request.getName());
	}

}