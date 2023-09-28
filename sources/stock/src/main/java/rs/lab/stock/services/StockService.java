package rs.lab.stock.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.lab.stock.dto.StockDto;
import rs.lab.stock.models.StockEntity;
import rs.lab.stock.models.StockRepository;

import java.util.Optional;

@Slf4j
@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public Page<StockEntity> getStocks(Pageable pageable) {
        return stockRepository.findAll(pageable);
    }

    public Optional<StockEntity> getStock(String item) {
        return stockRepository.findByItem(item);
    }

    public StockEntity addStock(StockDto stock) {
        var oldStock = stockRepository.findByItem(stock.getItem());

        StockEntity stockEntity;
        if (oldStock.isPresent()) {
            stockEntity = oldStock.get();
            stockEntity.setQuantity(stockEntity.getQuantity() + stock.getQuantity());
        } else {
            stockEntity = StockEntity.builder()
                    .item(stock.getItem())
                    .quantity(stock.getQuantity())
                    .build();
        }

        stockRepository.save(stockEntity);
        return stockEntity;
    }
}

