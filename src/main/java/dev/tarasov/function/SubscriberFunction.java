package dev.tarasov.function;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.LambdaRuntime;
import dev.tarasov.model.Subscriber;
import dev.tarasov.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class SubscriberFunction {

    private final LambdaLogger logger = LambdaRuntime.getLogger();
    private final SubscriberRepository subscriberRepository;

    @Bean
    public Supplier<List<Subscriber>> findAll() {
        return () -> {
            logger.log("Invoking findAll function");
            return subscriberRepository.findAll();
        };
    }

    @Bean
    public Consumer<Subscriber> save() {
        return (subscriber) -> {
            logger.log("Invoking save function");
            subscriberRepository.save(subscriber);
        };
    }

    @Bean
    public Function<Long, Subscriber> findById() {
        return (id) -> {
            logger.log("Invoking findById function");
            return subscriberRepository.findById(id).orElseThrow(RuntimeException::new);
        };
    }

}
