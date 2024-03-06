package dto.req;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrdersRequestDto {

    private Long userId;
    private Map<Long, Integer> productWithQuantity;

    @Builder
    public OrdersRequestDto(Long userId, Map<Long, Integer> productWithQuantity) {
        this.userId = userId;
        this.productWithQuantity = new LinkedHashMap<>(productWithQuantity);
    }
}
