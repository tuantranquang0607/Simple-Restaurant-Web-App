package nbcc.restaurantteam6foxtrot.services;

import nbcc.restaurantteam6foxtrot.entities.Event;
import nbcc.restaurantteam6foxtrot.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService
{
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository)
    {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAll()
    {
        return eventRepository.findAll();
    }

    public Optional<Event> findById(long id)
    {
        return eventRepository.findById(id);
    }
}
