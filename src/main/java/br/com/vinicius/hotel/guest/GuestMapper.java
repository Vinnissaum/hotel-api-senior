package br.com.vinicius.hotel.guest;

import org.springframework.stereotype.Component;

@Component
public class GuestMapper {

    public Guest toEntity(GuestDTO dto) {
        Guest entity = new Guest();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDocument(dto.getDocument());
        entity.setPhone(dto.getPhone());

        return entity;
    }

    public GuestDTO toDTO(Guest entity) {
        GuestDTO dto = new GuestDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDocument(entity.getDocument());
        dto.setPhone(entity.getPhone());

        return dto;
    }

}
