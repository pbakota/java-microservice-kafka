package rs.lab.common.dto;

import lombok.Data;

@Data
public class CustomerOrder {
    private String item;
    private Integer quantity;
    private Double amount;
    private String paymentMethod;
    private Long orderId;
    private String address;
}
