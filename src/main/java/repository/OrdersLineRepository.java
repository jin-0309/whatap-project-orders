package repository;

import entity.OrdersLine;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OrdersLineRepository implements PanacheRepository<OrdersLine> {

}
