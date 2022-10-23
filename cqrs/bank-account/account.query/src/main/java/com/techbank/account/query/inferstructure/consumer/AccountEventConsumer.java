package com.techbank.account.query.inferstructure.consumer;

import com.techbank.account.common.events.*;
import com.techbank.account.query.inferstructure.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer  implements  EventConsumer{

    @Autowired
    EventHandler eventHandler;



    @KafkaListener(topics = "AccountOpenEvent",groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consumer(AccountOpenEvent event, Acknowledgment ack) {
        if ( event.getState()!=null && !event.getState().endsWith("ACCOUNT_PENDING"))
        eventHandler.on(event);
        ack.acknowledge();

    }
    @KafkaListener(topics = "FundsDepositEvent",groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consumer(FundsDepositEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();

    }
    @KafkaListener(topics = "FundsWithdrawEvent",groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consumer(FundsWithdrawEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();

    }
    @KafkaListener(topics = "AccountCloseEvent",groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consumer(AccountCloseEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();

    }
    @KafkaListener(topics = "UserEvent",groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consumer(UserEvent event, Acknowledgment ack) {
        if ( event.getState()!=null && event.getState().endsWith("ACCOUNT_COMPLETED"))
          eventHandler.on(event);
        ack.acknowledge();

    }
}
