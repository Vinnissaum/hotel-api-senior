package br.com.vinicius.hotel.checkin;

import br.com.vinicius.hotel.guest.Guest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "checkin")
@Data
@NoArgsConstructor
public class CheckIn {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false)
    private UUID id;

    @CreationTimestamp
    @Column(nullable = false,  columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime arrivalAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime leftAt;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    private Boolean vehicleAdditionalCost = false;

}
