package nbcc.restaurantteam6foxtrot.controllers.api;

import nbcc.restaurantteam6foxtrot.dtos.DTOConverters;
import nbcc.restaurantteam6foxtrot.dtos.ReservationRequestDTO;
import nbcc.restaurantteam6foxtrot.entities.ReservationRequest;
import nbcc.restaurantteam6foxtrot.repositories.ReservationRequestRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationRequestApiController
{
    private final ReservationRequestRepository reservationRequestRepository;

    public ReservationRequestApiController(ReservationRequestRepository reservationRequestRepository)
    {
        this.reservationRequestRepository = reservationRequestRepository;
    }

    @GetMapping
    public List<ReservationRequestDTO> getAllReservations()
    {
        List<ReservationRequest> requests = reservationRequestRepository.findAll();
        return requests.stream().map(DTOConverters::toReservationRequestDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationRequestDTO> getReservationById(@PathVariable Long id)
    {
        Optional<ReservationRequest> rrOpt = reservationRequestRepository.findById(id);

        if (rrOpt.isPresent())
        {
            ReservationRequestDTO dto = DTOConverters.toReservationRequestDTO(rrOpt.get());

            return ResponseEntity.ok(dto);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
