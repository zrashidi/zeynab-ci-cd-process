package com.techbank.cqrs.core.event;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "eventStore")
public class EventModel{
    @Id
    private  String id;
    private Date timestamp;
    private String aggregateIdentifier;
    private  String aggregateType;
    private int version;
    private  String eventType;
    private BaseEvent eventData;


}
