package nbcc.restaurantteam6foxtrot.services;

import nbcc.restaurantteam6foxtrot.entities.ReservationRequest;

public interface EmailService {
    void sendReservationConfirmation(ReservationRequest reservationRequest);
    void sendReservationStatusUpdate(ReservationRequest reservationRequest);
}
