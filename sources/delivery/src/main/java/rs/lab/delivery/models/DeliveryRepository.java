package rs.lab.delivery.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends PagingAndSortingRepository<DeliveryEntity, Long>, CrudRepository<DeliveryEntity, Long> {
}
