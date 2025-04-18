package nbcc.restaurantteam6foxtrot.controllers;

import jakarta.validation.Valid;
import nbcc.restaurantteam6foxtrot.entities.*;
import nbcc.restaurantteam6foxtrot.repositories.*;
import nbcc.restaurantteam6foxtrot.services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping({"/","/event"})
public class EventController
{
    private final EventRepository eventRepository;
    private final SeatingTimeRepository seatingTimeRepository;
    private final DiningTableRepository diningTableRepository;
    private final LayoutRepository layoutRepository;
    private final LoginService loginService;
    private final ReservationRequestRepository reservationRequestRepository;

    public EventController(EventRepository eventRepository, SeatingTimeRepository seatingTimeRepository, DiningTableRepository diningTableRepository, LayoutRepository layoutRepository, LoginService loginService, ReservationRequestRepository reservationRequestRepository)
    {
        this.eventRepository = eventRepository;
        this.seatingTimeRepository = seatingTimeRepository;
        this.diningTableRepository = diningTableRepository;
        this.layoutRepository = layoutRepository;
        this.loginService = loginService;
        this.reservationRequestRepository = reservationRequestRepository;
    }

    @GetMapping()
    public String getAll(@RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate, Model model)
    {
//        var values = eventRepository.findAll();
//        model.addAttribute("events", values);

        List<Event> events;

        if (startDate != null && endDate != null)
        {
            events = eventRepository.findByStartDateBetween(startDate, endDate);
        }
        else if (startDate != null)
        {
            events = eventRepository.findByStartDateAfter(startDate);
        }
        else if (endDate != null)
        {
            events = eventRepository.findByStartDateBefore(endDate);
        }
        else
        {
//            events = eventRepository.findAll();
            events = eventRepository.findByArchivedFalse();
        }

        model.addAttribute("events", events);

        return "/event/index";
    }

    @GetMapping("/create")
    public String create(Model model)
    {
        if(!loginService.IsCurrenLoginValid()) return "redirect:/login";
        var layouts = layoutRepository.findByArchived(false);
        model.addAttribute("layouts", layouts);
        model.addAttribute("event", new Event());


        return "/event/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("event") Event event, BindingResult result)
    {
        if(!loginService.IsCurrenLoginValid()) return "redirect:/login";
        if (result.hasErrors()) {

            return "/event/create";
        }
        UserDetail user = loginService.getLoggedInUser();
        event.setOwner(user);
        eventRepository.save(event);

        return "redirect:/event";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id, @RequestParam(value = "message", defaultValue = "") String msg)
    {
        var entity = eventRepository.findById(id);
        List<ReservationRequest> reservations = reservationRequestRepository.findBySeatingTime_Event_IdAndStatus(id, ReservationStatus.valueOf("APPROVED"));

        if (entity.isPresent())
        {
            Event event = entity.get();
            List<SeatingTime> seatings = seatingTimeRepository.findAllByEvent(event);

            model.addAttribute("event", event);
            model.addAttribute("seatings", seatings);
            model.addAttribute("reservations", reservations);
            model.addAttribute("message", msg);

            return "/event/detail";
        }

        return "redirect:/event";
    }

    @GetMapping("/delete/{id}")
    public String confirmDelete(Model model, @PathVariable long id)
    {
        var entity = eventRepository.findById(id);

        if (entity.isPresent())
        {
            model.addAttribute("event", entity.get());
            return "/event/delete";
        }

        return "redirect:/event";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable long id)
    {
        var entity = eventRepository.findById(id);

        if (entity.isPresent())
        {
            Event event = entity.get();

            if (event.getEndDate().isBefore(LocalDate.now()))
            {
                event.setArchived(true);
                eventRepository.save(event);
            }
            else
            {
                eventRepository.delete(event);
            }
        }

        return "redirect:/event";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable long id)
    {
        var entity = eventRepository.findById(id);

        if (entity.isPresent()) {
            var event = entity.get();
            var layouts = layoutRepository.findByArchived(false);


            var currentLayout = event.getLayout();


            if (currentLayout != null && !layouts.contains(currentLayout)) {
                layouts.add(currentLayout);
            }

            model.addAttribute("layouts", layouts);
            model.addAttribute("event", event);

            return "/event/edit";
        }

        return "redirect:/event";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable long id, @ModelAttribute Event event)
    {
        var existingEvent = eventRepository.findById(id);

        if (existingEvent.isPresent())
        {
            event.setId(id);

            eventRepository.save(event);
        }

        return "redirect:/event";
    }


}
