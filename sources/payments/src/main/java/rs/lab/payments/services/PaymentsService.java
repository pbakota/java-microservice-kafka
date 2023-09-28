package rs.lab.payments.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.lab.payments.models.PaymentEntity;
import rs.lab.payments.models.PaymentsRepository;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    public Page<PaymentEntity> getPayments(Pageable pageable) {
        return paymentsRepository.findAll(pageable);
    }
}
