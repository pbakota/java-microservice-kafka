package rs.lab.stock.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockRepository extends PagingAndSortingRepository<StockEntity, Long>, CrudRepository<StockEntity, Long> {

    @Query("SELECT s FROM StockEntity s WHERE s.item=:item")
    Optional<StockEntity> findByItem(@Param("item") String item);
}
