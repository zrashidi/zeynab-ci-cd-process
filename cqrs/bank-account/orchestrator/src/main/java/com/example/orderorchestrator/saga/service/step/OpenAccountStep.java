package com.example.orderorchestrator.saga.service.step;

import com.example.orderorchestrator.saga.service.dto.OpenAccountResponse;
import com.example.orderorchestrator.saga.service.WorkflowStep;
import com.example.orderorchestrator.saga.service.WorkflowStepStatus;
import com.example.orderorchestrator.saga.service.dto.UserDTO;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class OpenAccountStep implements WorkflowStep {



    private final WebClient webClient;
    private final UserDTO requestDTO;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;

    public OpenAccountStep(WebClient webClient, UserDTO requestDTO) {
        this.webClient = webClient;
        this.requestDTO = requestDTO;
    }

    @Override
    public WorkflowStepStatus getStatus() {
        return this.stepStatus;
    }

    @Override
    public Mono<Boolean> process() {

        return this.webClient
                .post()
                .uri("opi/v1/user")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(OpenAccountResponse.class)
                .map(r ->  { if(r.getMessage().equals(OpenStatus.ACCOUNT_COMPLETED))
                {
                    return true;
                }
                  return false;
                })
                .doOnNext(b -> this.stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return this.webClient
                .post()
                .uri("opi/v1/user/update")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .map(r ->true)
                .onErrorReturn(false);
    }
}
