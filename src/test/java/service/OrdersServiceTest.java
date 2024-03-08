package service;

import static org.junit.jupiter.api.Assertions.*;

import dto.req.OrdersLineRequestDto;
import dto.req.OrdersRequestDto;
import dto.req.OrdersUpdateRequestDto;
import dto.res.OrdersResponseDto;
import entity.Orders;
import entity.OrdersLine;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import repository.OrdersLineRepository;
import repository.OrdersRepository;

@QuarkusTest
@Transactional
class OrdersServiceTest {

    @Inject
    OrdersRepository ordersRepository;

    @Inject
    OrdersService ordersService;

    @Test
    void save() {
        OrdersLineRequestDto ordersLineRequestDto1 = OrdersLineRequestDto.builder()
                .ordersLineId(1L)
                .productId(1L)
                .quantity(1)
                .build();
        OrdersRequestDto ordersRequestDto = OrdersRequestDto.builder()
                .userId(1L)
                .ordersLineRequestDto(new ArrayList<>(List.of(ordersLineRequestDto1)))
                .build();
        Long ordersId = ordersService.save(ordersRequestDto);
        Orders orders = ordersRepository.findById(ordersId);
        Assertions.assertEquals(orders.getOrdersId(), ordersId);
    }

    @Test
    void findById() {
        OrdersLineRequestDto ordersLineRequestDto1 = OrdersLineRequestDto.builder()
                .ordersLineId(1L)
                .productId(1L)
                .quantity(1)
                .build();
        OrdersRequestDto ordersRequestDto = OrdersRequestDto.builder()
                .userId(1L)
                .ordersLineRequestDto(new ArrayList<>(List.of(ordersLineRequestDto1)))
                .build();
        Long ordersId = ordersService.save(ordersRequestDto);
        OrdersResponseDto ordersResponseDto = ordersService.findById(ordersId);
        Assertions.assertEquals(ordersResponseDto.getOrderId(), ordersId);
    }

    @Test
    void findAll() {
        OrdersLineRequestDto ordersLineRequestDto1 = OrdersLineRequestDto.builder()
                .ordersLineId(1L)
                .productId(1L)
                .quantity(1)
                .build();
        OrdersRequestDto ordersRequestDto1 = OrdersRequestDto.builder()
                .userId(1L)
                .ordersLineRequestDto(new ArrayList<>(List.of(ordersLineRequestDto1)))
                .build();
        OrdersRequestDto ordersRequestDto2 = OrdersRequestDto.builder()
                .userId(2L)
                .ordersLineRequestDto(new ArrayList<>(List.of(ordersLineRequestDto1)))
                .build();
        Long ordersId1 = ordersService.save(ordersRequestDto1);
        Long ordersId2 = ordersService.save(ordersRequestDto2);
        List<OrdersResponseDto> result = ordersService.findAll();
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().anyMatch(dto -> dto.getOrderId().equals(ordersId1)));
        Assertions.assertTrue(result.stream().anyMatch(dto -> dto.getOrderId().equals(ordersId2)));
    }

    @Test
    void deleteById() {
        OrdersLineRequestDto ordersLineRequestDto1 = OrdersLineRequestDto.builder()
                .ordersLineId(1L)
                .productId(1L)
                .quantity(1)
                .build();
        OrdersRequestDto ordersRequestDto = OrdersRequestDto.builder()
                .userId(1L)
                .ordersLineRequestDto(new ArrayList<>(List.of(ordersLineRequestDto1)))
                .build();
        Long ordersId = ordersService.save(ordersRequestDto);
        Assertions.assertNotNull(ordersRepository.findById(ordersId));
        ordersService.deleteById(ordersId);
        Assertions.assertNull(ordersRepository.findById(ordersId));
    }

    @Test
    void update() {
        OrdersLineRequestDto ordersLineRequestDto1 = OrdersLineRequestDto.builder()
                .productId(1L)
                .quantity(1)
                .build();
        OrdersLineRequestDto ordersLineRequestDto2 = OrdersLineRequestDto.builder()
                .productId(2L)
                .quantity(1)
                .build();
        OrdersRequestDto ordersRequestDto = OrdersRequestDto.builder()
                .userId(1L)
                .ordersLineRequestDto(new ArrayList<>(List.of(ordersLineRequestDto1)))
                .build();
        Long ordersId = ordersService.save(ordersRequestDto);
        OrdersUpdateRequestDto ordersUpdateRequestDto = OrdersUpdateRequestDto.builder()
                .ordersId(ordersId)
                .ordersLineRequestDto(new ArrayList<>(List.of(ordersLineRequestDto2)))
                .build();
        ordersService.update(ordersUpdateRequestDto);
        Assertions.assertEquals(2L, ordersRepository.findById(ordersId).getOrdersLines().stream().findFirst().get().getProductId());
    }
}