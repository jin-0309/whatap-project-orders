package dto.req;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrdersRequestDto {

    private Long userId;
    private List<OrdersLineRequestDto> ordersLineRequestDto;

    @Builder
    public OrdersRequestDto(Long userId, List<OrdersLineRequestDto> ordersLineRequestDto) {
        this.userId = userId;
        this.ordersLineRequestDto = ordersLineRequestDto;
    }
}
