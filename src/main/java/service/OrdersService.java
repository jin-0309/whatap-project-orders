package service;

import dto.req.OrdersRequestDto;
import entity.Orders;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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
