package com.techbank.account.query.inferstructure.consumer;

import com.techbank.account.common.events.*;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void  consumer(@Payload AccountOpenEvent event, Acknowledgment ack);
    void  consumer(@Payload FundsDepositEvent event, Acknowledgment ack);
    void  consumer(@Payload FundsWithdrawEvent event, Acknowledgment ack);
    void  consumer(@Payload AccountCloseEvent event, Acknowledgment ack);
    void  consumer(@Payload UserEvent event, Acknowledgment ack);
}
