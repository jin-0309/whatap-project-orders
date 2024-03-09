package service;

import exception.ProductNotFoundException;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestTransaction
class ProductServiceTest {

    @Inject
    ProductService productService;

    @Test
    void getProductById() {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProductById(0L));
    }
}