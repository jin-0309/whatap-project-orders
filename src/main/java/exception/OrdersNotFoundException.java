package exception;

public class OrdersNotFoundException extends CustomException {
    public OrdersNotFoundException() {
        super("해당 주문을 찾을 수 없습니다.");
    }
}
