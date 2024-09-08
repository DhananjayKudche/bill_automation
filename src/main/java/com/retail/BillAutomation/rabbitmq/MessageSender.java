package com.retail.BillAutomation.rabbitmq;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.BillAutomation.data.UserData;

@Service
public class MessageSender {

	private RabbitTemplate rabbitTemplate;

	private MessageSender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Value("${rabbitmq.queue.exchange}")
	private String EXCHANGE_NAME;

	@Value("${rabbitmq.queue.name}")
	private String QUEUE_NAME;

	@Value("${rabbitmq.queue.routingkey}")
	private String ROUTING_KEY;

	public void sendMessage(List<UserData> userDatafromBillService) {
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        // Convert the list of users to JSON
	        String userDataToPublish = objectMapper.writeValueAsString(userDatafromBillService);
	        
	        // Send the JSON string to the RabbitMQ queue
	        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, userDataToPublish);
	    } catch (JsonProcessingException e) {
	        // Handle the exception
	        e.printStackTrace();
	    }
	}
}