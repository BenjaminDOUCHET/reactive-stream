package com.example.classic.controller;


import com.example.classic.model.Item;
import com.example.classic.repositories.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
	private final ItemRepository itemRepository;

	@GetMapping(produces = "text/csv")
	public ResponseEntity<String> getAllCSV(@RequestParam(name = "delay", required = false, defaultValue = "0") long delay) {
		StringBuilder csvBuilder = new StringBuilder();
		csvBuilder.append("brand;sellerRef;quantity;unitPrice\n");
		List<Item> items = itemRepository.findAll();
		simulateProcessing(items, delay);
		items.forEach(item -> csvBuilder.append(itemToCsv(item)));
		String filename = "items.csv";
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
				.contentType(MediaType.parseMediaType("text/csv"))
				.body(csvBuilder.toString());
	}
	private void simulateProcessing(List<Item> item, long processingTime) {
		try {
			Thread.sleep(processingTime*item.size());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
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
