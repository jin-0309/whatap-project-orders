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

    public int save(Orders orders, Map<Long, Integer> productWithQuantity) {
        int count = 0;
        for (Map.Entry<Long, Integer> info : productWithQuantity.entrySet()) {
            OrdersLine ordersLine = OrdersLine.builder()
                   .orders(orders)
                   .productId(info.getKey())
                   .quantity(info.getValue())
                   .build();
           ordersLineRepository.persist(ordersLine);
           count++;
        }
        return count;
    }
}
