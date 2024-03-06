package service;

import entity.Orders;
import entity.OrdersLine;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Map;
import repository.OrdersLineRepository;

@ApplicationScoped
public class OrdersLineService {

    OrdersLineRepository ordersLineRepository;

    @Inject
    public OrdersLineService(OrdersLineRepository ordersLineRepository) {
        this.ordersLineRepository = ordersLineRepository;
    }

    public void save(Orders orders, Map<Long, Integer> productWithQuantity) {
        for (Map.Entry<Long, Integer> info : productWithQuantity.entrySet()) {
            OrdersLine ordersLine = OrdersLine.builder()
                   .orders(orders)
                   .productId(info.getKey())
                   .quantity(info.getValue())
                   .build();
           ordersLineRepository.persist(ordersLine);
           orders.addOrderLine(ordersLine);
        }
    }
}
