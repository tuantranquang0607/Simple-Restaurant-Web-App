package nbcc.restaurantteam6foxtrot.controllers.api;

import nbcc.restaurantteam6foxtrot.dtos.DTOConverters;
import nbcc.restaurantteam6foxtrot.dtos.EventDTO;
import nbcc.restaurantteam6foxtrot.entities.Event;
import nbcc.restaurantteam6foxtrot.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/event")
public class EventApiController
{
    private final EventService eventService;

    public EventApiController(EventService eventService)
    {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventDTO> getAllEvents()
    {
        List<Event> events = eventService.getAll();
        return DTOConverters.toEventDTO(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable long id)
    {
        Optional<Event> eventOpt = eventService.findById(id);

        if (eventOpt.isPresent())
        {
            EventDTO dto = DTOConverters.toEventDTO(eventOpt.get());

            return ResponseEntity.ok(dto);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
