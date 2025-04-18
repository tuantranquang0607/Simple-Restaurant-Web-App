package nbcc.restaurantteam6foxtrot.repositories;

import nbcc.restaurantteam6foxtrot.entities.Event;
import nbcc.restaurantteam6foxtrot.entities.Layout;
import nbcc.restaurantteam6foxtrot.entities.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>
{
    List<Event> findByStartDateBetween(LocalDate start, LocalDate end);
    List<Event> findByStartDateAfter(LocalDate start);
    List<Event> findByStartDateBefore(LocalDate end);
    List<Event> findByArchivedFalse();

    Collection<Object> findAllByLayout(Layout layout);

    Object findByOwnerNot(UserDetail owner);

    List<Event> findAllByOwner_Id(long ownerId);

    List<Event> findAllByOwner(UserDetail owner);

    List<Event> findAllByOwnerNot(UserDetail owner);
}
