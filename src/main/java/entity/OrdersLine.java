package entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders_line")
public class OrdersLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_line_id")
    private Long ordersLineId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @Column(name = "product_id")
    private Long productId;

    private int quantity;

    @Builder
    public OrdersLine(Long ordersLineId, Orders orders, Long productId, int quantity) {
        this.ordersLineId = ordersLineId;
        this.orders = orders;
        this.productId = productId;
        this.quantity = quantity;
    }

    public void deleteOrderLine() {
        this.orders = null;
    }
}
