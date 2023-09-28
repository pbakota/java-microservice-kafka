package rs.lab.stock.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.lab.stock.dto.StockDto;
import rs.lab.stock.dto.mappers.StockMapper;
import rs.lab.stock.services.StockService;

import java.util.Collection;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockMapper mapper;

    @GetMapping("/items")
    public ResponseEntity<Collection<StockDto>> getStocks(@PageableDefault Pageable pageable) {
        var stocks = stockService.getStocks(pageable);
        return ResponseEntity.ok(mapper.toStocksDto(stocks));
    }

    @GetMapping("/items/{name}")
    public ResponseEntity<StockDto> getStocks(@RequestParam String name) {
        var stock = stockService.getStock(name);
        if(stock.isPresent()) {
            ResponseEntity.ok(mapper.toStockDto(stock.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/items")
    public ResponseEntity addStock(@RequestBody StockDto stock) {
        var stockEntity = stockService.addStock(stock);
        return ResponseEntity.ok(mapper.toStockDto(stockEntity));
    }
}
