package br.com.vinicius.hotel.checkin;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/checkIns")
@AllArgsConstructor
public class CheckInController {

    private final CheckInService service;

    @GetMapping
    public ResponseEntity<List<CheckInDTO>> index(
            @RequestParam(value = "order", defaultValue = "ASC") String order) {
        List<CheckInDTO> checkInDTOList = service.findAll(Sort.by(Sort.Direction
                .valueOf(order.toUpperCase()), "arrivalAt"));

        return ResponseEntity.ok(checkInDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckInDTO> findById(@PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/getCheckIn")
    public ResponseEntity<List<CheckInDTO>> find(
            @RequestParam(value = "guestName", required = false) String name,
            @RequestParam(value = "guestDocument", required = false) String document,
            @RequestParam(value = "guestPhone", required = false) String phone) {

        return ResponseEntity.ok(service.filter(name, document, phone));
    }

    @GetMapping("/getActive")
    public ResponseEntity<List<CheckInDTO>> getActiveCheckIns() {
        return ResponseEntity.ok(service.getActiveCheckIns());
    }

    @GetMapping("/getInactive")
    public ResponseEntity<List<CheckInDTO>> getInactiveCheckIns() {
        return ResponseEntity.ok(service.getInactiveCheckIns());
    }

    @PostMapping
    public ResponseEntity<CheckInDTO> checkIn(@RequestBody @Valid CheckInDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<CheckInDTO> checkOut(@PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(service.checkOut(id));
    }

}
