package repository;

import entity.OrdersLine;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrdersLineRepository implements PanacheRepository<OrdersLine> {
}
