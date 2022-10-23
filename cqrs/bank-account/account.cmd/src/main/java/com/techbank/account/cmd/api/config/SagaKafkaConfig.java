package com.techbank.account.cmd.api.config;

import com.techbank.account.common.dto.OrchestratorRequestDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxSink;


@Configuration
public class SagaKafkaConfig {

    @Bean
    public DirectProcessor<OrchestratorRequestDTO> publisher(){

        return DirectProcessor.create();
    }

    @Bean
    public FluxSink<OrchestratorRequestDTO> sink(DirectProcessor<OrchestratorRequestDTO> publisher){

        return publisher.sink();
    }


}
