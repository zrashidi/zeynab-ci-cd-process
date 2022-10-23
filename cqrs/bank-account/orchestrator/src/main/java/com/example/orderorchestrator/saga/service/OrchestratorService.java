package com.example.orderorchestrator.saga.service;


import com.example.orderorchestrator.saga.service.dto.UserDTO;
import com.example.orderorchestrator.saga.service.dto.OrchestratorResponseDTO;
import com.example.orderorchestrator.saga.service.step.OpenAccountStep;
import com.example.orderorchestrator.saga.service.step.OpenStatus;
import com.techbank.account.common.dto.OrchestratorRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrchestratorService {

    WebClient saveUser = WebClient.create("http://localhost:5006");
    Boolean b=true;
    public Mono<OrchestratorResponseDTO> orderProduct(final OrchestratorRequestDTO requestDTO){
        Workflow orderWorkflow = this.getOrderWorkflow(requestDTO);
        return Flux.fromStream(() -> orderWorkflow.getSteps().stream())
                .flatMap(WorkflowStep::process)
             /*   .handle(((b, synchronousSink) -> {
                 *//*   if(b) {
                       // requestDTO.setState("ACCOUNT_COMPLETED");
                        synchronousSink.next(true);
                    }
                    else {
                       // requestDTO.setState("ACCOUNT_CANCELLED");
                        synchronousSink.error(new Exception("create open failed!"));
                    }*//*
                    if (true){
                        synchronousSink.next(true);
                    }
                }))*/
                .then(Mono.fromCallable(() -> getResponseDTO(requestDTO, OpenStatus.ACCOUNT_COMPLETED)))
                .onErrorResume(ex -> this.revert(orderWorkflow, requestDTO,OpenStatus.ACCOUNT_CANCELLED));

    }


    private OrchestratorResponseDTO getResponseDTO(OrchestratorRequestDTO requestDTO, OpenStatus status){
        OrchestratorResponseDTO responseDTO = new OrchestratorResponseDTO();
        responseDTO.setName(requestDTO.getName());
        responseDTO.setFamily(requestDTO.getFamily());
        responseDTO.setAge(requestDTO.getAge());
        responseDTO.setAccountId(requestDTO.getAccountId());
        responseDTO.setState(status.name());

        return responseDTO;
    }

    private Mono<OrchestratorResponseDTO> revert(final Workflow workflow, final OrchestratorRequestDTO requestDTO,OpenStatus status){
        return Flux.fromStream(() -> workflow.getSteps().stream())
                .filter(wf -> {wf.getStatus().equals(WorkflowStepStatus.FAILED);
                    System.out.println("********wf.getStatus()********** " +wf.getStatus());
                    return  true;

                }
                )
                .flatMap(WorkflowStep::revert)
                //.retry(1)
                .then(Mono.just(this.getResponseDTO(requestDTO, OpenStatus.ACCOUNT_CANCELLED)));
    }
    private Workflow getOrderWorkflow(OrchestratorRequestDTO requestDTO){
        WorkflowStep openAccountStep = new OpenAccountStep(this.saveUser, this.getRequestDTO(requestDTO));

        return new OpenAccountWorkflow(List.of(openAccountStep));
    }
    private UserDTO getRequestDTO(OrchestratorRequestDTO requestDTO){
        UserDTO userDTO=  new UserDTO();
        userDTO.setName(requestDTO.getName());
        userDTO.setFamily(requestDTO.getFamily());
        userDTO.setAge(requestDTO.getAge());
        userDTO.setAccountId(requestDTO.getAccountId());
       // userDTO.setState("ACCOUNT_COMPLETED");

        return userDTO;
    }


}
