package br.com.vinicius.hotel.guest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface GuestRepository extends JpaRepository<Guest, UUID> {}
