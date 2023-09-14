package br.com.vinicius.hotel.guest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/guests")
@AllArgsConstructor
public class GuestController {

    private final GuestService service;

    @GetMapping
    public ResponseEntity<List<GuestDTO>> index(
            @RequestParam(value = "order", defaultValue = "ASC") String order) {
        List<GuestDTO> contacts = service.findAll(Sort.by(Sort.Direction
                .valueOf(order.toUpperCase()), "name"));

        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> findById(@PathVariable @NotNull UUID id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<GuestDTO> save(@RequestBody @Valid GuestDTO dto) {
        GuestDTO guestDTO = service.create(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(location).body(guestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> update(@PathVariable @NotNull UUID id, @RequestBody @Valid GuestDTO dto) {
        GuestDTO guestDTO = service.update(id, dto);

        return ResponseEntity.ok(guestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}