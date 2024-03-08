package service;

import static org.junit.jupiter.api.Assertions.*;

import adapter.ProductAdapter;
import dto.res.ProductResponseDto;
import exception.ProductNotFoundException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class ProductServiceTest {

    @Inject
    ProductService productService;

    @Test
    void getProductById() {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProductById(0L));
    }
}