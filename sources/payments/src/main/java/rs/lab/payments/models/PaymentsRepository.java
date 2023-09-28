package rs.lab.payments.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PaymentsRepository extends PagingAndSortingRepository<PaymentEntity, Long>, CrudRepository<PaymentEntity, Long> {

    @Query("SELECT p FROM PaymentEntity p WHERE p.orderId=:order_id")
    Iterable<PaymentEntity> findAllByOrderId(@Param("order_id") Long orderId);
}
