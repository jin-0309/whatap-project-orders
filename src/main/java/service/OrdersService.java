package service;

import dto.req.OrdersLineRequestDto;
import dto.req.OrdersRequestDto;
import dto.req.OrdersUpdateRequestDto;
import dto.res.OrdersResponseDto;
import dto.res.OrdersResponseDto.OrdersLineInfo;
import dto.res.ProductResponseDto;
import entity.Orders;
import entity.OrdersLine;
import exception.OrdersNotFoundException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
                .totalPrice(getTotalPrice(dto.getOrdersLineRequestDto()))
                .build();
        for (OrdersLineRequestDto ordersLineRequestDto : dto.getOrdersLineRequestDto()) {
            ordersLineService.save(orders, ordersLineRequestDto);
        }
        ordersRepository.persist(orders);
        return orders.getOrdersId();
    }

    public OrdersResponseDto findById(Long id) {
        Orders orders = ordersRepository.findByIdOptional(id).orElseThrow(OrdersNotFoundException::new);
        return toDto(orders);
    }

    public List<OrdersResponseDto> findAll() {
        PanacheQuery<Orders> dtos = ordersRepository.findAll();
        return dtos.stream().map(this::toDto).toList();
    }

    public void deleteById(Long ordersId) {
        Optional<Orders> orders = ordersRepository.findByIdOptional(ordersId);
        ordersRepository.delete(orders.orElseThrow(OrdersNotFoundException::new));
    }

    public Long update(OrdersUpdateRequestDto dto) {
        Orders orders = ordersRepository.findByIdOptional(dto.getOrdersId()).orElseThrow(OrdersNotFoundException::new);
        orders.removeOrderLine(ordersLineService.findByOrdersId(dto.getOrdersId()));
        orders.updateOrders(ordersLineService.getOrdersLinesByDtos(dto.getOrdersLineRequestDto(), orders),
                getTotalPrice(dto.getOrdersLineRequestDto()));
        ordersRepository.persist(orders);
        return orders.getOrdersId();
    }

    private OrdersResponseDto toDto(Orders orders) {
        return OrdersResponseDto.builder()
                .orderId(orders.getOrdersId())
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
                .ordersLineId(ordersLine.getOrdersLineId())
                .productId(ordersLine.getProductId())
                .productName(productResponseDto.getName())
                .quantity(ordersLine.getQuantity())
                .subPrice(ordersLine.getQuantity()*productResponseDto.getPrice())
                .build();
    }

    private Double getTotalPrice(List<OrdersLineRequestDto> dto) {
        double totalPrice = 0.0;
        for (OrdersLineRequestDto ordersLine : dto) {
            Double price = productService.getProductById(ordersLine.getProductId()).getPrice();
            totalPrice += price * ordersLine.getQuantity();
        }
        return totalPrice;
    }
}
