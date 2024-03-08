package service;

import adapter.ProductAdapter;
import dto.res.ProductResponseDto;
import exception.ProductNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Transactional
public class ProductService {

    @RestClient
    ProductAdapter productAdapter;

    public ProductResponseDto getProductById(Long id) {
        ProductResponseDto productResponseDto;
        try {
            productResponseDto = productAdapter.getProduct(id);
        } catch (RuntimeException e) {
            throw new ProductNotFoundException();
        }
        return productResponseDto;
    }

}
