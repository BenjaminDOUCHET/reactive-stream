package com.example.classic.repositories;


import com.example.classic.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ItemRepository extends MongoRepository<Item,String> {
	List<Item> findAll();
}
