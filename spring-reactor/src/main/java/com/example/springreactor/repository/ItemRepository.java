package com.example.springreactor.repository;
import com.example.springreactor.model.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


public interface ItemRepository extends ReactiveMongoRepository<Item,String> {
    Flux<Item> findAll();
}
