package com.cleartax.superhero.services;

import com.cleartax.superhero.dto.Superhero;
import com.cleartax.superhero.dto.SuperheroRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.*;

import software.amazon.awssdk.services.sqs.SqsClient;

import java.util.List;
import com.cleartax.superhero.repository.SuperheroRepository;
@Service
public class Consumerservice {
    @Autowired
    private SqsClient sqsClient;
    @Autowired
    private SuperheroRepository superheroRepository;
    @Autowired
    private SuperheroService superheroService;
    @Value("${sqs.queue.url}")
    private String queueUrl;

    // Inject the queue URL from application.properties
    public Consumerservice(SqsClient sqsClient, @Value("${sqs.queue.url}") String queueUrl) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
    }

    // Scheduled task to run every 5 seconds (you can adjust the interval as needed)
    @Scheduled(fixedRate = 5000)  // Every 5 seconds
    public void receiveAndProcessMessages() {
        // Create a receive message request
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)  // Fetch up to 10 messages at once (max allowed by SQS)
                .waitTimeSeconds(10)      // Enable long polling (wait up to 10 seconds for messages)
                .build();

        // Receive the messages from the queue
        ReceiveMessageResponse response = sqsClient.receiveMessage(receiveMessageRequest);

        List<Message> messages = response.messages();
        if (messages.isEmpty()) {
            System.out.println("No messages in the queue to process.");
        } else {
            // Process each message
            for (Message message : messages) {
                System.out.println("Processing message: " + message.body());

                // After processing the message, delete it from the queue
                deleteMessage(message);
            }
        }
    }

    // Method to delete a message from the queue after it has been processed
    private void deleteMessage(Message message) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())  // The receipt handle is used to identify the message to delete
                .build();

        String superHeroName = message.body();
        Superhero superhero = superheroRepository.findByName(superHeroName)
                .orElseThrow(() -> new RuntimeException("Superhero not found with name: " + superHeroName));
        superhero.setName("Hardik");

        superheroService.updateSuperhero(superHeroName,superhero);
        System.out.println("Message with ID " + message.messageId() + " "
                + message.body() + " has been deleted from the queue.");
    }
}
