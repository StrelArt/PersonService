package telran.java58.person.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
     @NotNull(message = "id must be provided")
     private Integer id;
     @NotBlank(message = "name must be provided")
     private String name;
     @NotNull(message = "birthDate must be provided")
     @Past(message = "birthDate must be in the past")
     private LocalDate birthDate;
     @NotNull(message = "address must be provided")
     @Valid
     private AddressDto address;
}
