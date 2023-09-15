package br.com.vinicius.hotel.checkin;

import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static br.com.vinicius.hotel.checkin.QCheckIn.checkIn;

class CheckInRepositoryCustomImpl implements CheckInRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CheckIn> getInactiveCheckIns() {
        return new JPAQuery<>(entityManager) //
                .select(checkIn) //
                .from(checkIn) //
                .where(checkIn.leftAt.before(LocalDateTime.now())) //
                .fetch();
    }

    @Override
    public List<CheckIn> getActiveCheckIns() {
        return new JPAQuery<>(entityManager) //
                .select(checkIn) //
                .from(checkIn) //
                .where(checkIn.leftAt.isNull()) //
                .fetch();
    }
}
