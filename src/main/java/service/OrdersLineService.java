package service;

import dto.res.OrderResponseDto.OrdersLineInfo;
import dto.res.ProductResponseDto;
import entity.Orders;
import entity.OrdersLine;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import repository.OrdersLineRepository;

@ApplicationScoped
public class OrdersLineService {

    OrdersLineRepository ordersLineRepository;
    ProductService productService;

    @Inject
    public OrdersLineService(OrdersLineRepository ordersLineRepository, ProductService productService) {
        this.ordersLineRepository = ordersLineRepository;
        this.productService = productService;
    }

    public void save(Orders orders, Map<Long, Integer> productWithQuantity) {
        for (Map.Entry<Long, Integer> info : productWithQuantity.entrySet()) {
            OrdersLine ordersLine = OrdersLine.builder()
                   .orders(orders)
                   .productId(info.getKey())
                   .quantity(info.getValue())
                   .build();
           orders.addOrdersLine(ordersLine);
        }
    }
}
