package service;

import dto.req.OrdersRequestDto;
import dto.res.OrderResponseDto;
import dto.res.OrderResponseDto.OrdersLineInfo;
import dto.res.ProductResponseDto;
import entity.Orders;
import entity.OrdersLine;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import repository.OrdersRepository;

@ApplicationScoped
@Transactional
public class OrdersService {

    OrdersLineService ordersLineService;
    OrdersRepository ordersRepository;
    ProductService productService;

    @Inject
    public OrdersService(OrdersLineService ordersLineService, OrdersRepository ordersRepository, ProductService productService) {
        this.ordersLineService = ordersLineService;
        this.ordersRepository = ordersRepository;
        this.productService = productService;
    }

    public Long save(OrdersRequestDto dto) {
        Orders orders = Orders.builder()
                .userId(dto.getUserId())
                .orderDate(LocalDateTime.now())
                .totalPrice(getTotalPrice(dto))
                .build();
        ordersLineService.save(orders, dto.getProductWithQuantity());
        ordersRepository.persist(orders);
        return orders.getId();
    }

    public OrderResponseDto findById(Long id) {
        Orders orders = ordersRepository.findByIdOptional(id).orElseThrow();
        return toDto(orders);
    }

    public List<OrderResponseDto> findAll() {
        PanacheQuery<Orders> dtos = ordersRepository.findAll();
        return dtos.stream().map(this::toDto).toList();
    }

    private OrderResponseDto toDto(Orders orders) {
        return OrderResponseDto.builder()
                .orderId(orders.getId())
                .userId(orders.getUserId())
                .orderDate(orders.getOrderDate())
                .modifiedDate(orders.getModifiedDate())
                .totalPrice(orders.getTotalPrice())
                .ordersLineInfos(orders.getOrdersLines().stream().map(this::toInfo).toList())
                .build();
    }

    private OrdersLineInfo toInfo(OrdersLine ordersLine) {
        ProductResponseDto productResponseDto = productService.getProductById(ordersLine.getProductId());
        return OrdersLineInfo.builder()
                .ordersLineId(ordersLine.getId())
                .productId(ordersLine.getProductId())
                .productName(productResponseDto.getName())
                .quantity(ordersLine.getQuantity())
                .subPrice(ordersLine.getQuantity()*productResponseDto.getPrice())
                .build();
    }

    private Double getTotalPrice(OrdersRequestDto dto) {
        double totalPrice = 0.0;
        for (Map.Entry<Long, Integer> order : dto.getProductWithQuantity().entrySet()) {
            Double price = productService.getProductById(order.getKey()).getPrice();
            int quantity = order.getValue();
            totalPrice += price*quantity;
        }
        return totalPrice;
    }
}
