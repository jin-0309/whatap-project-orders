package dto.req;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrdersUpdateRequestDto {

    private Long ordersId;
    private List<OrdersLineRequestDto> ordersLineRequestDto;

    @Builder
    public OrdersUpdateRequestDto(Long ordersId, List<OrdersLineRequestDto> ordersLineRequestDto) {
        this.ordersId = ordersId;
        this.ordersLineRequestDto = ordersLineRequestDto;
    }
}
