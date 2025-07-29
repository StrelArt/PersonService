package telran.java58.person.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChildDto.class, name = "child"),
        @JsonSubTypes.Type(value = EmployeeDto.class, name = "employee"),
        @JsonSubTypes.Type(value = PersonDto.class, name = "person")
})
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
