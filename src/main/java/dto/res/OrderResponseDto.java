package dto.res;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponseDto {

    private Long orderId;
    private Long userId;
    private LocalDateTime orderDate;
    private LocalDateTime modifiedDate;
    private Double totalPrice;
    private List<OrdersLineInfo> ordersLineInfos = new ArrayList<>();

    @Builder
    public OrderResponseDto(Long orderId, Long userId, LocalDateTime orderDate, LocalDateTime modifiedDate,
                            Double totalPrice, List<OrdersLineInfo> ordersLineInfos) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.modifiedDate = modifiedDate;
        this.totalPrice = totalPrice;
        this.ordersLineInfos = ordersLineInfos;
    }

    @Getter
    @Builder
    public static class OrdersLineInfo {
        private Long ordersLineId;
        private Long productId;
        private String productName;
        private int quantity;
        private Double subPrice;
    }

}
