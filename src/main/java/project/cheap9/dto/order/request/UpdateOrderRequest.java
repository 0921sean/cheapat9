package project.cheap9.dto.order.request;

import lombok.Data;
import project.cheap9.domain.OrderStatus;

@Data
public class UpdateOrderRequest {
    private OrderStatus status;
}
