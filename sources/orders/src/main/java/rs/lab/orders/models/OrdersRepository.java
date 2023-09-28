package rs.lab.orders.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends PagingAndSortingRepository<OrderEntity, Long>, CrudRepository<OrderEntity, Long> {
}
