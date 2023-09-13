package br.com.vinicius.hotel.checkin;

import br.com.vinicius.hotel.guest.GuestMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CheckInMapper {

    private GuestMapper guestMapper;

    public CheckIn toEntity(CheckInDTO dto) {
        CheckIn entity = new CheckIn();
        entity.setId(dto.getId());
        entity.setArrivalAt(dto.getArrivalAt());
        entity.setLeftAt(dto.getLeftAt());
        entity.setGuest(guestMapper.toEntity(dto.getGuest()));

        return entity;
    }

    public CheckInDTO toDTO(CheckIn entity) {
        CheckInDTO dto = new CheckInDTO();
        dto.setId(entity.getId());
        dto.setArrivalAt(entity.getArrivalAt());
        dto.setLeftAt(entity.getLeftAt());
        dto.setGuest(guestMapper.toDTO(entity.getGuest()));

        return dto;
    }

}
