package com.techbank.account.cmd.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techbank.account.cmd.api.command.OpenAccoundCommand;
import com.techbank.account.common.dto.OrchestratorRequestDTO;
import com.techbank.cqrs.core.inferstructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Configuration
public class OpenAccountEventHandler {

    @Autowired
    private DirectProcessor<OrchestratorRequestDTO> source;
    @Autowired
    private CommandDispatcher commandDispatcher;


    @Bean
    public Supplier<Flux<OrchestratorRequestDTO>> supplier(){

        return () -> Flux.from(source);
    };

    @Bean
    public Consumer<Flux<OrchestratorRequestDTO>> consumer(){
        return (flux) -> flux
                            .subscribe(responseDTO -> {
                                //TODO Update OR INSERT
                                        System.out.println("State:  "+responseDTO.getState());
                                        System.out.println("AccountId:  "+responseDTO.getAccountId());
                                        OpenAccoundCommand command= new OpenAccoundCommand();
                                        command.setState("responseDTO.getState()");
                                        command.setId(responseDTO.getAccountId());
                                        commandDispatcher.send(command);

                            }


                            );
    };

 /*   @Bean
    public Supplier<Flux<OrchestratorRequestDTO>> supplier() {
        return () -> Flux.fromStream(Stream.generate(new Supplier<OrchestratorRequestDTO>() {
            @Override
            public OrchestratorRequestDTO get() {
                try {
                    Thread.sleep(1000);
                    return new OrchestratorRequestDTO();
                } catch (Exception e) {
                    e.printStackTrace();
                    // ignore
                   return null;
                }

            }

        })).subscribeOn(Schedulers.elastic()).share();
    }*/
/* @Bean
 public MappingJackson2MessageConverter mappingJackson2MessageConverter(ObjectMapper objectMapper) {
     MappingJackson2MessageConverter jacksonMessageConverter = new MappingJackson2MessageConverter();
     jacksonMessageConverter.setObjectMapper(objectMapper);
     jacksonMessageConverter.setSerializedPayloadClass(String.class);
     jacksonMessageConverter.setStrictContentTypeMatch(true);
     return jacksonMessageConverter;
 }*/
}
