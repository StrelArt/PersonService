package telran.java58.person.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @NotBlank(message = "city must be provided")
    private String city;
    @NotBlank(message = "street must be provided")
    private String street;
    @NotNull(message = "building must be provided")
    @Min(value = 1, message = "building must be greater than 0")
    private Integer building;
}
