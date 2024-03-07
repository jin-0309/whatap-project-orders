package dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrdersLineRequestDto {

    private Long ordersLineId;
    private Long productId;
    private int quantity;

    @Builder
    public OrdersLineRequestDto(Long ordersLineId, Long productId, int quantity) {
        this.ordersLineId = ordersLineId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
