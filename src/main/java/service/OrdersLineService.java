package service;

import dto.req.OrdersLineRequestDto;
import dto.req.OrdersUpdateRequestDto;
import entity.Orders;
import entity.OrdersLine;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import repository.OrdersLineRepository;

@ApplicationScoped
@Transactional
public class OrdersLineService {

    OrdersLineRepository ordersLineRepository;
    ProductService productService;

    @Inject
    public OrdersLineService(OrdersLineRepository ordersLineRepository, ProductService productService) {
        this.ordersLineRepository = ordersLineRepository;
        this.productService = productService;
    }

    public OrdersLine save(Orders orders, OrdersLineRequestDto dto) {
        OrdersLine ordersLine = OrdersLine.builder()
               .orders(orders)
               .productId(dto.getProductId())
               .quantity(dto.getQuantity())
               .build();
       orders.addOrdersLine(ordersLine);
       ordersLineRepository.persist(ordersLine);
       return ordersLine;
    }

    public List<OrdersLine> findByOrdersId(Long ordersId) {
        return ordersLineRepository.findByOrdersId(ordersId);
    }

    public List<OrdersLine> getOrdersLinesByDtos(List<OrdersLineRequestDto> dtos, Orders orders) {
        List<OrdersLine> ordersLines = new ArrayList<>();
        for (OrdersLineRequestDto dto : dtos) {
            ordersLines.add(save(orders, dto));
        }
        return ordersLines;
    }

}
