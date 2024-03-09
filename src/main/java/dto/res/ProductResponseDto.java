package dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponseDto {

    private Long id;
    private String name;
    private Double price;
    private String description;


    @Builder
    public ProductResponseDto(Long id, String name, Double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
