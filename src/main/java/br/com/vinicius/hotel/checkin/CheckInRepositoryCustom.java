package br.com.vinicius.hotel.checkin;

import java.util.List;

interface CheckInRepositoryCustom {

    List<CheckIn> getInactiveCheckIns();

    List<CheckIn> getActiveCheckIns();

}
