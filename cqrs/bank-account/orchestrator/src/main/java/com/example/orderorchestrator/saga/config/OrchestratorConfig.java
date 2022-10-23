package com.example.orderorchestrator.saga.config;


import com.example.orderorchestrator.saga.service.OrchestratorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techbank.account.common.dto.OrchestratorRequestDTO;
import com.example.orderorchestrator.saga.service.dto.OrchestratorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import reactor.core.publisher.Flux;

import java.util.function.Function;


@Configuration
public class OrchestratorConfig {


    @Autowired
    private OrchestratorService orchestratorService;

    @Bean
    public Function<Flux<OrchestratorRequestDTO>, Flux<OrchestratorResponseDTO>> processor(){
        return flux -> flux
                .flatMap(dto -> this.orchestratorService.orderProduct(dto))
                .doOnNext(dto -> System.out.println("Status ********** : " + dto.getState()));
    }
    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter jacksonMessageConverter = new MappingJackson2MessageConverter();
        jacksonMessageConverter.setObjectMapper(objectMapper);
        jacksonMessageConverter.setSerializedPayloadClass(String.class);
        jacksonMessageConverter.setStrictContentTypeMatch(true);
        return jacksonMessageConverter;
    }

}
