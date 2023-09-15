package br.com.vinicius.hotel.checkin;

import br.com.vinicius.hotel.guest.GuestDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CheckInDTO {
    private UUID id;

    private LocalDateTime arrivalAt;

    private LocalDateTime leftAt;

    private GuestDTO guest;

    private Boolean vehicleAdditionalCost = false;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal totalValue;

}
