package nbcc.restaurantteam6foxtrot.controllers;

import jakarta.validation.Valid;
import nbcc.restaurantteam6foxtrot.entities.*;
import nbcc.restaurantteam6foxtrot.repositories.DiningTableRepository;
import nbcc.restaurantteam6foxtrot.repositories.EventRepository;
import nbcc.restaurantteam6foxtrot.repositories.ReservationRequestRepository;
import nbcc.restaurantteam6foxtrot.repositories.SeatingTimeRepository;
import nbcc.restaurantteam6foxtrot.services.EmailService;
import nbcc.restaurantteam6foxtrot.services.LoginService;
import nbcc.restaurantteam6foxtrot.services.UserService;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRequestRepository reservationRequestRepository;
    private final EventRepository eventRepository;
    private final SeatingTimeRepository seatingTimeRepository;
    private final LoginService loginService;
    private final EmailService emailService;
    private final DiningTableRepository diningTableRepository;

    public ReservationController(ReservationRequestRepository reservationRequestRepository,
                                 EventRepository eventRepository,
                                 SeatingTimeRepository seatingTimeRepository, UserService userService, LoginService loginService, EmailService emailService, DiningTableRepository diningTableRepository) {
        this.reservationRequestRepository = reservationRequestRepository;
        this.eventRepository = eventRepository;
        this.seatingTimeRepository = seatingTimeRepository;
        this.emailService = emailService;
        this.loginService = loginService;
        this.diningTableRepository = diningTableRepository;
    }

    @GetMapping("/{eventId}/{seatingId}/create")
    public String createReservationForm(Model model, @PathVariable long eventId, @PathVariable long seatingId) {
        var event = eventRepository.findById(eventId);
        var seatingTime = seatingTimeRepository.findById(seatingId);
        if(event.isEmpty() || seatingTime.isEmpty()) {
            return "redirect:/event";
        }
        model.addAttribute("event", event.get());
        model.addAttribute("seatingTime", seatingTime.get());

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setSeatingTime(seatingTime.get());

        model.addAttribute("reservationRequest", reservationRequest);




        return "reservations/create";
    }

    @PostMapping("/create")
    public String createReservation(@Valid @ModelAttribute("reservation") ReservationRequest reservationRequest, BindingResult result) {

        if (result.hasErrors()) {
            return "reservations/create";
        }
        reservationRequestRepository.save(reservationRequest);
        emailService.sendReservationConfirmation(reservationRequest);

         return "redirect:/event/" + reservationRequest.getSeatingTime().getEvent().getId()
                + "?message=Reservation%20successfully%20created,%20It%20is%20under%20review";
    }

    @GetMapping({"", "/"})
    public String getAll(
            @RequestParam(value = "view", defaultValue = "requests") String view,
            @RequestParam(value = "eventId", required = false, defaultValue = "all") String eventId,
            @RequestParam(value = "status", required = false, defaultValue = "all") String status,
            Model model) {

        if (!loginService.IsCurrenLoginValid()) return "redirect:/login";

        UserDetail user = loginService.getLoggedInUser();

        List<ReservationRequest> reservations;
        List<Event> events;

        if (view.equals("requests")) {
            events = eventRepository.findAll();

            if ("all".equals(eventId) && "all".equals(status)) {
                reservations = reservationRequestRepository.findAll();
            } else if (!"all".equals(eventId) && "all".equals(status)) {
                reservations = reservationRequestRepository.findBySeatingTime_Event_Id(Long.parseLong(eventId));
            } else if ("all".equals(eventId)) {
                reservations = reservationRequestRepository.findByStatus(ReservationStatus.valueOf(status));
            } else {
                reservations = reservationRequestRepository.findBySeatingTime_Event_IdAndStatus(Long.parseLong(eventId), ReservationStatus.valueOf(status));
            }
        } else {
            return "redirect:/reservations";
        }



        model.addAttribute("view", view);
        model.addAttribute("reservations", reservations);
        model.addAttribute("events", events);
        model.addAttribute("selectedEvent", eventId);
        model.addAttribute("selectedStatus", status);

        return "reservations/index";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id)
    {
        var entity = reservationRequestRepository.findById(id);


        if (entity.isPresent())
        {
            ReservationRequest reservationRequest = entity.get();

            model.addAttribute("reservationRequest", reservationRequest);


            return "reservations/detail";
        }

        return "redirect:/reservations";
    }

    @PostMapping("/{reservationId}/changeStatus")
    public String changeStatus(@PathVariable long reservationId,
                               @RequestParam("status") ReservationStatus status,
                               @RequestParam(value = "tableId", required = false) Long tableId) {

        if (!loginService.IsCurrenLoginValid()) return "redirect:/login";

        UserDetail user = loginService.getLoggedInUser();
        ReservationRequest request = reservationRequestRepository.findById(reservationId).orElse(null);

        if (request == null || !request.getSeatingTime().getEvent().getOwner().equals(user)) {
            return "redirect:/reservations";
        }

        if (status == ReservationStatus.APPROVED) {
            if (tableId == null) {
                return "redirect:/reservations";
            }

            DiningTable selectedTable = diningTableRepository.findById(tableId).orElse(null);
            if (selectedTable == null) {
                return "redirect:/reservations";
            }

            request.setTable(selectedTable);
        } else {
            request.setTable(null);
        }

        request.setStatus(status);
        reservationRequestRepository.save(request);
        emailService.sendReservationStatusUpdate(request);

        return "redirect:/reservations?view=requests";
    }


}
