package rs.lab.payments.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import rs.lab.payments.dto.PaymentDto;
import rs.lab.payments.models.PaymentEntity;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {
    PaymentDto toPaymentDto(PaymentEntity payment);

    Collection<PaymentDto> toPaymentsDto(Page<PaymentEntity> payments);
}
