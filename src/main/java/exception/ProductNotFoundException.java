package exception;

public class ProductNotFoundException extends CustomException {
    public ProductNotFoundException() {
        super("해당 상품을 찾을 수 없습니다.");
    }
}
