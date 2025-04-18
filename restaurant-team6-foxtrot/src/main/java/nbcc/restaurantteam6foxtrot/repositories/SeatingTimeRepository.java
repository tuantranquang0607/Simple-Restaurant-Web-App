package nbcc.restaurantteam6foxtrot.repositories;

import nbcc.restaurantteam6foxtrot.entities.Event;
import nbcc.restaurantteam6foxtrot.entities.SeatingTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatingTimeRepository extends JpaRepository<SeatingTime, Long> {
    List<SeatingTime> findByEvent(Event event);

    List<SeatingTime> findAllByEvent(Event event);
}
