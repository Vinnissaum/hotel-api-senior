package br.com.vinicius.hotel.checkin;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CheckInDTO {
    private UUID id;

    private LocalDateTime arrivalAt;

    private LocalDateTime leftAt;

    private UUID guestId;

}
