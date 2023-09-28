package rs.lab.payments.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDto {
    private long id;
    private String mode;
    private long orderId;
    private double amount;
    private String status;
}
