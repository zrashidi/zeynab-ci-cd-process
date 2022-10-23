package com.example.orderorchestrator.saga.service.dto;

import com.example.orderorchestrator.saga.service.step.OpenStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrchestratorResponseDTO implements Serializable {

    private  String state;
    private  String age;
    private String name;
    private String family;
    private String accountId;
}
