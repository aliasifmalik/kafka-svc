package com.javainuse.service;

import com.javainuse.request.EmployeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public final class ProducerService {
	private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

	@Value(value = "${general.topic.name}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value(value = "${user.topic.name}")
	private String userTopicName;

	@Autowired
	private KafkaTemplate<String, EmployeeDto> userKafkaTemplate;

	public void sendMessage(String message) {
		ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topicName, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Sent message: " + message
						+ " with offset: " + result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Unable to send message : " + message, ex);
			}
		});
	}

	public void saveCreateUserLog(EmployeeDto request) {
		ListenableFuture<SendResult<String, EmployeeDto>> future
				= this.userKafkaTemplate.send(userTopicName, request);

		future.addCallback(new ListenableFutureCallback<SendResult<String, EmployeeDto>>() {
			@Override
			public void onSuccess(SendResult<String, EmployeeDto> result) {
				logger.info("User created: "
						+ request + " with offset: " + result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("User created : " + request, ex);
			}
		});
	}
}
