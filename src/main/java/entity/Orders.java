package entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Nullable
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrdersLine> ordersLines = new ArrayList<>();

    @Builder
    public Orders(Long userId, LocalDateTime orderDate, Double totalPrice) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public void addOrdersLine(OrdersLine ordersLine) {
        this.ordersLines.add(ordersLine);
    }
}
