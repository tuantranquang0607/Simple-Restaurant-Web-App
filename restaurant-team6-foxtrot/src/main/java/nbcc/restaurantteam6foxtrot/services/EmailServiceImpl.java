package nbcc.restaurantteam6foxtrot.services;

import nbcc.restaurantteam6foxtrot.entities.ReservationRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void sendReservationStatusUpdate(ReservationRequest reservationRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reservationRequest.getEmail());
        message.setSubject("Reservation Status Update");
        message.setText("Hello " + reservationRequest.getFullName() + ",\n\n"
                + "Your reservation status has been updated to: " + reservationRequest.getStatus() + ".\n\n"
                + "Event: " + reservationRequest.getSeatingTime().getEvent().getName() + "\n"
                + "Start Date: " + reservationRequest.getSeatingTime().getFormattedStartDate() + "\n"
                + "Duration: " + "From " + reservationRequest.getSeatingTime().getFormatStartDateTime() + ", Till " + reservationRequest.getSeatingTime().getFormattedEndDateTime() +"\n\n"
                + "Best regards,\nThe Events Team");

        mailSender.send(message);
    }

    @Override
    public void sendReservationConfirmation(ReservationRequest reservationRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(reservationRequest.getEmail());
        message.setSubject("Reservation Request Received");
        message.setText("Hello " + reservationRequest.getFullName() + ",\n\n"
                + "Your reservation request has been received.\n\n"
                + "Event: " + reservationRequest.getSeatingTime().getEvent().getName() + "\n"
                + "Start Date: " + reservationRequest.getSeatingTime().getFormattedStartDate() + "\n"
                + "Duration: " + "From " + reservationRequest.getSeatingTime().getFormattedStartDateTime() + ", Till " + reservationRequest.getSeatingTime().getFormattedEndDateTime() +"\n\n"
                + "Status: " + reservationRequest.getStatus() + "\n\n"
                + "Thank you!");

        mailSender.send(message);
    }
}
