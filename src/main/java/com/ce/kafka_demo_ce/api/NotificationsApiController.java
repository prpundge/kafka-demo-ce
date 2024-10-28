package com.ce.kafka_demo_ce.api;


import org.openapispec.api.NotificationsApi;
import org.openapispec.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class NotificationsApiController implements NotificationsApi {

    private static final Logger log = LoggerFactory.getLogger(NotificationsApiController.class);
    private final List<Notification> notifications = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1); // Simple ID generator

    @Override
    public ResponseEntity<Notification> notificationsPost(@Valid @RequestBody Notification notification) {

        log.info("start");
        if (notification.getTitle() == null || notification.getMessage() == null || notification.getRecipient() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Set the ID and created timestamp
        notification.setId(idCounter.getAndIncrement());
//        notification.setCreatedAt(OffsetDateTime.from(LocalDateTime.now()));

        // Save to in-memory storage
        notifications.add(notification);

        // Return the created notification with status 201
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }
}
