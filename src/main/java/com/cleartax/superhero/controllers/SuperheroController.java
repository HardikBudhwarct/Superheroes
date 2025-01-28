package com.cleartax.superhero.controllers;

import com.cleartax.superhero.dto.Superhero;
import com.cleartax.superhero.dto.SuperheroRequestBody;
import com.cleartax.superhero.services.SuperheroService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import com.cleartax.superhero.Config.SqsConfig;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

@RestController
public class SuperheroController {

    @Autowired
    private SuperheroService superheroService;
    @Autowired
    private SqsConfig sqsConfig;
    @Autowired
    private SqsClient sqsClient;

    public SuperheroController(SuperheroService superheroService) {
        this.superheroService = superheroService;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "username", defaultValue = "World") String username ,@RequestBody String heroName) {
        // Send a message to SQS (Existing logic)
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(sqsConfig.getQueueUrl())
                .messageBody(heroName)
                .build());
        return String.format("Hello %s!, %s", username, sqsConfig.getQueueName());
    }

    @GetMapping("/superhero")
    public ResponseEntity<Superhero> getSuperhero(@RequestParam(value = "name", defaultValue = "Batman") String name,
                                                  @RequestParam(value = "universe", defaultValue = "DC") String universe) {
        // Retrieve superhero details from the service
        Optional<Superhero> s = superheroService.getSuperhero(name, universe);
        if (s.isPresent()) {
            return new ResponseEntity<>(s.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/superhero")
    public Superhero persistSuperhero(@RequestBody SuperheroRequestBody SuperHeross) {
        return superheroService.persistSuperhero(SuperHeross);
    }

    @PutMapping("/{id}")
    public Superhero updateSuperhero(@PathVariable String id, @RequestBody Superhero superhero) {
        return superheroService.updateSuperhero(id, superhero);
    }

    @DeleteMapping("/{id}")
    public void deleteSuperhero(@PathVariable String id) {
        superheroService.deleteSuperhero(id);
    }

    @GetMapping("/readFromQueue")
    public String readFromQueue() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(sqsConfig.getQueueUrl())
                .maxNumberOfMessages(1)
                .waitTimeSeconds(10)
                .build();
        ReceiveMessageResponse result = sqsClient.receiveMessage(receiveMessageRequest);

        if (!result.messages().isEmpty()) {

            String messageBody = result.messages().get(0).body();
        }
        return "No message in the queue";
    }
}
