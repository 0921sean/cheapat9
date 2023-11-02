package project.cheap9.dto.order.response;

import lombok.Data;

@Data
public class UpdateOrderResponse {
    private Long id;

    public UpdateOrderResponse(Long id) {
        this.id = id;
    }
}
