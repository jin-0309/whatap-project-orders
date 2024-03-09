package service;

import dto.req.OrdersLineRequestDto;
import dto.req.OrdersRequestDto;
import dto.req.OrdersUpdateRequestDto;
import dto.res.OrdersResponseDto;
import dto.res.ProductResponseDto;
import entity.Orders;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.OrdersRepository;

@QuarkusTest
@TestTransaction
class OrdersServiceTest {

    @Inject
    OrdersRepository ordersRepository;

    @Inject
    OrdersService ordersService;

    @InjectMock
    ProductService productService;

    @BeforeEach
    void setUp() {
        ProductResponseDto productResponseDto1 = ProductResponseDto.builder()
                .id(1L)
                .name("test")
                .price(1000.)
                .description("test")
                .build();
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .id(2L)
                .name("test")
                .price(1000.)
                .description("test")
                .build();
        Mockito.when(productService.getProductById(1L)).thenReturn(productResponseDto1);
        Mockito.when(productService.getProductById(2L)).thenReturn(productResponseDto);
    }

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
        for (OrdersResponseDto dto : result) {
            System.out.println(dto.getOrderId());
        }
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
        Assertions.assertEquals(2L,
                ordersRepository.findById(ordersId).getOrdersLines().stream().findFirst().get().getProductId());
    }
}