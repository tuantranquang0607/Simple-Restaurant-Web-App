package nbcc.restaurantteam6foxtrot.repositories;

import nbcc.restaurantteam6foxtrot.entities.DiningTable;
import nbcc.restaurantteam6foxtrot.entities.ReservationRequest;
import nbcc.restaurantteam6foxtrot.entities.ReservationStatus;
import nbcc.restaurantteam6foxtrot.entities.UserDetail;
import org.hibernate.mapping.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Long> {

    List<ReservationRequest> findBySeatingTime_Event_Id(long seatingTimeEventId);

    List<ReservationRequest> findByStatus(ReservationStatus status);

    List<ReservationRequest> findBySeatingTime_Event_IdAndStatus(long seatingTimeEventId, ReservationStatus status);

    boolean existsByTable(DiningTable table);
}

