package br.com.vinicius.hotel.guest;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
public class GuestDTO {

    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    private String document;

    private String phone;

    public GuestDTO(Guest entity) {
        this.setId(entity.getId());
        this.setName(entity.getName());
        this.setDocument(entity.getDocument());
        this.setPhone(entity.getPhone());
    }

}
