package com.example.springreactor.controller;


import com.example.springreactor.model.Item;
import com.example.springreactor.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@AllArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemRepository itemRepository;

    @GetMapping(produces = "text/csv")
    public Flux<String> getAllCSV(@RequestParam(required = false, defaultValue = "0") long delay) {
        Flux<String> header = Flux.just("brand;sellerRef;quantity;unitPrice\n");
        Flux<String> data = itemRepository.findAll().delayElements(Duration.ofMillis(delay)).map(this::itemToCsv);
        return Flux.concat(header, data);
    }

    private String itemToCsv(Item item) {
        return String.join(";",
                item.getBrand(),
                item.getSellerRef(),
                String.valueOf(item.getQuantity()),
                String.valueOf(item.getUnitPrice())
        ) + "\n";
    }

}
