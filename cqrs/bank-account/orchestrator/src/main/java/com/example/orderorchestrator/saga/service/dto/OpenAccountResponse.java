package com.example.orderorchestrator.saga.service.dto;

import lombok.Data;

@Data
public class OpenAccountResponse {

    private  String message;
    private  String age;
    private String name;
    private String family;
    private   String accountId;
    private  String state;
}
