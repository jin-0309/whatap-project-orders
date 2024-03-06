package repository;

import entity.Orders;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrdersRepository implements PanacheRepository<Orders> {
}
