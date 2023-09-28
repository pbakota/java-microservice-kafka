package rs.lab.delivery.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryDto {
    private long id;
    private String address;
    private String status;
    private long orderId;
}
