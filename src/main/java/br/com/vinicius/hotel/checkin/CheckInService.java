package br.com.vinicius.hotel.checkin;

import br.com.vinicius.hotel.exceptions.ResourceNotFoundException;
import br.com.vinicius.hotel.guest.Guest;
import br.com.vinicius.hotel.guest.GuestDTO;
import br.com.vinicius.hotel.guest.GuestMapper;
import br.com.vinicius.hotel.guest.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.accessibility.AccessibleKeyBinding;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;

    private final GuestService guestService;

    private final CheckInMapper checkInMapper;

    private final GuestMapper guestMapper;

    private static final double businessDayHotelFee = 120.0;

    private static final double weekendDayHotelFee = 150.0;

    private static final double businessDayCarFee = 15.0;

    private static final double weekendDayCarFee = 20.0;

    private static final String NOT_FOUND = "Check-in not found with id: ";

    public List<CheckInDTO> findAll(Sort direction) {
        return checkInRepository.findAll(direction).stream().map(checkInMapper::toDTO).collect(Collectors.toList());
    }

    public CheckInDTO findById(UUID id) {
        Optional<CheckIn> opt = checkInRepository.findById(id);
        CheckIn checkIn = opt.orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND + id));
        CheckInDTO checkInDTO = checkInMapper.toDTO(checkIn);
        calculateTotalCost(checkInDTO);

        return checkInDTO;
    }

    @Transactional
    public CheckInDTO create(CheckInDTO dto) {
        CheckIn entity = checkInMapper.toEntity(dto);
        GuestDTO guestDTO = guestService.findById(dto.getGuest().getId());
        Guest guest = guestMapper.toEntity(guestDTO);
        entity.setGuest(guest);

        CheckIn save = checkInRepository.save(entity);
        return checkInMapper.toDTO(save);
    }

    public List<CheckInDTO> filter(String name, String document, String phone) {
        if (name != null && !name.isBlank() && !name.isEmpty()) {
            List<CheckInDTO> checkInDTOS = checkInRepository.findByGuestNameIgnoreCase(name)
                    .stream().map(checkInMapper::toDTO).collect(Collectors.toList());
            checkInDTOS.forEach(this::calculateTotalCost);
            return checkInDTOS;
        }

        if (document != null && !document.isBlank() && !document.isEmpty()) {
            List<CheckInDTO> checkInDTOS = checkInRepository.findByGuestDocument(document)
                    .stream().map(checkInMapper::toDTO).collect(Collectors.toList());
            checkInDTOS.forEach(this::calculateTotalCost);
            return checkInDTOS;
        }

        if (phone != null && !phone.isBlank() && !phone.isEmpty()) {
            List<CheckInDTO> checkInDTOS = checkInRepository.findByGuestPhone(phone)
                    .stream().map(checkInMapper::toDTO).collect(Collectors.toList());
            checkInDTOS.forEach(this::calculateTotalCost);
            return checkInDTOS;
        }

        return checkInRepository.findAll()
                .stream().map(checkInMapper::toDTO).collect(Collectors.toList());

    }

    public List<CheckInDTO> getActiveCheckIns() {
        List<CheckInDTO> checkInDTOS = checkInRepository.getActiveCheckIns().stream().map(checkInMapper::toDTO).collect(Collectors.toList());
        checkInDTOS.forEach(this::calculateTotalCost);
        return checkInDTOS;
    }

    public List<CheckInDTO> getInactiveCheckIns() {
        List<CheckInDTO> checkInDTOS = checkInRepository.getInactiveCheckIns().stream().map(checkInMapper::toDTO).collect(Collectors.toList());
        checkInDTOS.forEach(this::calculateTotalCost);
        return checkInDTOS;
    }

    @Transactional
    public CheckInDTO checkOut(UUID id) {
        CheckIn entity = checkInRepository.findById(id).orElseThrow( //
                () -> new ResourceNotFoundException(NOT_FOUND + id) //
        );

        entity.setLeftAt(LocalDateTime.now());
        CheckInDTO checkInDTO = checkInMapper.toDTO(checkInRepository.save(entity));
        calculateTotalCost(checkInDTO);

        return checkInDTO;
    }

    private void calculateTotalCost(CheckInDTO checkIn) {
        double totalValue = 0D;

        Map<String, Integer> days = countDaysBetweenDates(checkIn.getArrivalAt(), checkIn.getLeftAt());
        Integer businessDays = days.get("businessDays");
        Integer weekendDays = days.get("weekendDays");
        if (chargeExtraDaily(checkIn.getArrivalAt(), checkIn.getLeftAt())) {
            totalValue = totalValue + businessDayHotelFee;
        }
        if (checkIn.getVehicleAdditionalCost()) {
            totalValue = totalValue + (businessDays * businessDayCarFee) + (weekendDays * weekendDayCarFee);
        }

        totalValue = totalValue + (businessDays * businessDayHotelFee) + (weekendDays * weekendDayHotelFee);

        checkIn.setTotalValue(BigDecimal.valueOf(totalValue));
    }

    private boolean chargeExtraDaily(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate == null || endDate.getDayOfMonth() == startDate.getDayOfMonth()) {
            return false;
        }

        LocalTime limit = LocalTime.of(16, 30);
        return endDate.toLocalTime().isAfter(limit);
    }

    private Map<String, Integer> countDaysBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Integer> days = new HashMap<>();
        Integer businessDays = 0;
        Integer weekendDays = 0;

        if (endDate == null) {
            endDate = LocalDateTime.now();
        }
        LocalDateTime dateTime = startDate;
        while (!dateTime.isAfter(endDate)) {
            if (dateTime.getDayOfWeek() != DayOfWeek.SATURDAY && dateTime.getDayOfWeek() != DayOfWeek.SUNDAY) {
                businessDays++;
            } else {
                weekendDays++;
            }
            dateTime = dateTime.plusDays(1);
        }

        days.put("businessDays", businessDays);
        days.put("weekendDays", weekendDays);

        return days;
    }

}
