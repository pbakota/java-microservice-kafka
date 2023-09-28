package rs.lab.orders.dto.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import rs.lab.orders.dto.OrderDto;
import rs.lab.orders.models.OrderEntity;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderDto toOrderDto(OrderEntity entity);

    Collection<OrderDto> toOrdersDto(Page<OrderEntity> orders);
}
