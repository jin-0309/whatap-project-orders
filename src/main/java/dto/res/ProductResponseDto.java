package dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ProductResponseDto {

    private Long id;
    private String name;
    private Double price;
    private String description;

}
