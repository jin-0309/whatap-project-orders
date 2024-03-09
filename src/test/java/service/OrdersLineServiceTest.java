package service;

import dto.req.OrdersLineRequestDto;
import dto.req.OrdersRequestDto;
import entity.Orders;
import entity.OrdersLine;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.OrdersLineRepository;

@QuarkusTest
@Transactional
class OrdersLineServiceTest {

    @Inject
    OrdersLineService ordersLineService;

    @Inject
    OrdersLineRepository ordersLineRepository;

    @Inject
    OrdersService ordersService;

    @Test
    void save() {
        Orders orders = Orders.builder()
                .userId(1L)
                .totalPrice(1000.0)
                .orderDate(LocalDateTime.now())
                .build();
        OrdersLineRequestDto dto = OrdersLineRequestDto.builder()
                .productId(1L)
                .quantity(1)
                .build();
        OrdersLine ordersLine = ordersLineService.save(orders, dto);
        OrdersLine findOrdersLine = ordersLineRepository.findByIdOptional(ordersLine.getOrdersLineId()).get();
        Assertions.assertEquals(ordersLine, findOrdersLine);
    }

    @Test
    void findByOrdersId() {
        OrdersLineRequestDto ordersLineRequestDto1 = OrdersLineRequestDto.builder()
                .productId(1L)
                .quantity(1)
                .build();
        OrdersLineRequestDto ordersLineRequestDto2 = OrdersLineRequestDto.builder()
                .productId(1L)
                .quantity(2)
                .build();
        OrdersRequestDto ordersRequestDto = OrdersRequestDto.builder()
                .userId(1L)
                .ordersLineRequestDto(new ArrayList<>(List.of(ordersLineRequestDto1, ordersLineRequestDto2)))
                .build();
        Long ordersId = ordersService.save(ordersRequestDto);
        Assertions.assertEquals(2, ordersLineService.findByOrdersId(ordersId).size());
    }

    @Test
    void getOrdersLinesByDtos() {
        Orders orders = Orders.builder()
                .userId(1L)
                .totalPrice(1000.0)
                .orderDate(LocalDateTime.now())
                .build();
        OrdersLineRequestDto ordersLineRequestDto1 = OrdersLineRequestDto.builder()
                .productId(1L)
                .quantity(1)
                .build();
        OrdersLineRequestDto ordersLineRequestDto2 = OrdersLineRequestDto.builder()
                .productId(1L)
                .quantity(2)
                .build();
        OrdersLine ordersLine1 = ordersLineService.save(orders, ordersLineRequestDto1);
        OrdersLine ordersLine2 = ordersLineService.save(orders, ordersLineRequestDto2);
        List<OrdersLine> result = ordersLineService.getOrdersLines(
                List.of(ordersLineRequestDto1, ordersLineRequestDto2), orders);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(ordersLine1.getOrders(), result.get(0).getOrders());
        Assertions.assertEquals(ordersLine2.getOrders(), result.get(1).getOrders());
    }
}