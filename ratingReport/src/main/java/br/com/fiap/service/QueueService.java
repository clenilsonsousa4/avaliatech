package br.com.fiap.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class QueueService {
    private final SqsClient sqsClient;

    @ConfigProperty
    String queueUrl;

    public QueueService() {
        this.sqsClient = SqsClient.builder().region(Region.US_EAST_1).build();
    }

    public SendMessageResponse sendMessage(String message) {
        return sqsClient.sendMessage(m -> m.queueUrl(queueUrl).messageBody(message));
    }

    public Message receiveMessage(String message) {
        List<Message> messages = sqsClient.receiveMessage(r -> r.queueUrl(queueUrl).maxNumberOfMessages(10)).messages();

        if (!messages.isEmpty()) {
            sqsClient.deleteMessage(d -> d.queueUrl(queueUrl).receiptHandle(messages.getFirst().receiptHandle()));

            return messages.getFirst();
        }

        return null;
    }
}
