package br.com.vinicius.hotel.checkin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface CheckInRepository extends JpaRepository<CheckIn, UUID>, CheckInRepositoryCustom {

    List<CheckIn> findByGuestPhone(String phone);

    List<CheckIn> findByGuestNameIgnoreCase(String name);

    List<CheckIn> findByGuestDocument(String document);

}
