package br.com.vinicius.hotel.guest;

import br.com.vinicius.hotel.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    private static final String NOT_FOUND = "Guest not found with id: ";

    public List<GuestDTO> findAll(Sort direction) {
        return guestRepository.findAll(direction).stream().map(guestMapper::toDTO).collect(Collectors.toList());
    }

    public GuestDTO findById(UUID id) {
        Optional<Guest> opt = guestRepository.findById(id);
        Guest guest = opt.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND + id));

        return guestMapper.toDTO(guest);
    }

    @Transactional
    public GuestDTO create(GuestDTO dto) {
        Guest guest = guestRepository.save(guestMapper.toEntity(dto));

        return guestMapper.toDTO(guest);
    }

    @Transactional
    public GuestDTO update(UUID id, GuestDTO dto) {
        Guest entity = guestRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(NOT_FOUND + id)
        );
        BeanUtils.copyProperties(dto, entity, "id");

        return guestMapper.toDTO(guestRepository.save(entity));
    }

    public void deleteById(UUID id) {
        try {
            guestRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(NOT_FOUND + id);
        }
    }
}
