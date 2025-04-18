package nbcc.restaurantteam6foxtrot.controllers;

import nbcc.restaurantteam6foxtrot.Helpers.SeatingTimeUtils;
import nbcc.restaurantteam6foxtrot.Helpers.SeatingTimeValidator;
import nbcc.restaurantteam6foxtrot.entities.Event;
import nbcc.restaurantteam6foxtrot.entities.SeatingTime;
import nbcc.restaurantteam6foxtrot.repositories.SeatingTimeRepository;
import nbcc.restaurantteam6foxtrot.repositories.EventRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/event/{eventId}/seating")
public class SeatingTimeController {
    private final SeatingTimeRepository seatingTimeRepository;
    private final EventRepository eventRepository;

    public SeatingTimeController(SeatingTimeRepository seatingTimeRepository, EventRepository eventRepository) {
        this.seatingTimeRepository = seatingTimeRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/create")
    public String create(@PathVariable Long eventId, Model model) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            return "redirect:/event";
        }

        Event eventEntity = event.get();

        LocalDateTime eventStartDateTime = event.get().getStartDate().atStartOfDay();
        LocalDateTime eventEndDateTime = event.get().getEndDate().atTime(23, 59);

        int[] maxDuration = SeatingTimeUtils.calculateDuration(eventStartDateTime, eventEndDateTime);

        int maxHours = maxDuration[0];
        int maxMinutes = maxDuration[1];

        model.addAttribute("event", eventEntity);
        model.addAttribute("seating", new SeatingTime());
        model.addAttribute("maxHours", maxHours);
        model.addAttribute("maxMinutes", maxMinutes);

        return "seating/create";
    }

    @PostMapping("/create")
    public String create(@PathVariable Long eventId, @Valid @ModelAttribute("seating") SeatingTime seatingTime, BindingResult result, Model model) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            return "redirect:/event";
        }
        Event eventEntity = event.get();



        seatingTime.setEvent(eventEntity);

        int[] maxDuration = SeatingTimeValidator.validateSeatingTime(seatingTime, eventEntity, result);

        if (result.hasErrors()) {
            model.addAttribute("event", eventEntity);
            model.addAttribute("seating", seatingTime);
            model.addAttribute("maxHours", maxDuration[0]);
            model.addAttribute("maxMinutes", maxDuration[1]);
            return "seating/create";
        }



        seatingTimeRepository.save(seatingTime);
        return "redirect:/event/" + eventId;
    }

    // To work on

    @GetMapping("/{id}/edit")
    public String editSeating(@PathVariable Long eventId, @PathVariable Long id, Model model) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + eventId));
        SeatingTime seating = seatingTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seating Id:" + id));

        model.addAttribute("event", event);
        model.addAttribute("seating", seating);

        return "seating/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateSeating(@PathVariable Long eventId, @PathVariable Long id,
                                @ModelAttribute("seating") @Valid SeatingTime seatingTime,
                                BindingResult result, Model model) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (result.hasErrors()) {
            return "seating/edit";
        }

        Event eventEntity = event.get();



        seatingTime.setEvent(eventEntity);

        int[] maxDuration = SeatingTimeValidator.validateSeatingTime(seatingTime, eventEntity, result);

        if (result.hasErrors()) {
            model.addAttribute("event", eventEntity);
            model.addAttribute("seating", seatingTime);
            model.addAttribute("maxHours", maxDuration[0]);
            model.addAttribute("maxMinutes", maxDuration[1]);
            return "seating/edit";
        }

        seatingTime.setUpdatedAt(LocalDateTime.now());
        seatingTimeRepository.save(seatingTime);

        return "redirect:/event/" + eventId;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long eventId, @PathVariable Long id) {
        System.out.println("===============");
        System.out.println(id);
        System.out.println("===============");
        seatingTimeRepository.deleteById(id);
        return "redirect:/event/" + eventId;
    }
}
