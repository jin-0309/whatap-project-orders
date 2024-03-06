package service;

import adapter.ProductAdapter;
import dto.res.ProductResponseDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ProductService {

    @RestClient
    ProductAdapter productAdapter;

    public ProductResponseDto getProductById(Long id) {
        return productAdapter.getProduct(id);
    }

}
