package nbcc.restaurantteam6foxtrot.dtos;

import nbcc.restaurantteam6foxtrot.entities.*;

import java.util.ArrayList;
import java.util.List;

public class DTOConverters
{
    public static EventDTO toEventDTO(Event event)
    {
        EventDTO dto = new EventDTO();

        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setStartDate(event.getStartDate());
        dto.setEndDate(event.getEndDate());
        dto.setPrice(event.getPrice());
        dto.setCreatedDate(event.getCreatedDate());
        dto.setArchived(event.isArchived());

        if (event.getSeatingTimes() != null)
        {
            List<SeatingTimeDTO> seatingDTOs = new ArrayList<>();

            for (SeatingTime seating : event.getSeatingTimes())
            {
                SeatingTimeDTO stDto = new SeatingTimeDTO();
                stDto.setId(seating.getId());
                stDto.setStartDateTime(seating.getStartDateTime());
                seatingDTOs.add(stDto);
            }

            dto.setSeatingTimes(seatingDTOs);
        }

        return dto;
    }

    public static List<EventDTO> toEventDTO(Iterable<Event> events)
    {
        List<EventDTO> dtos = new ArrayList<>();

        for (Event event : events)
        {
            dtos.add(toEventDTO(event));
        }

        return dtos;
    }

    public static MenuDTO toMenuDTO(Menu menu)
    {
        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setDescription(menu.getDescription());
        dto.setCreated(menu.getCreated());

        if (menu.getItems() != null)
        {
            List<MenuItemDTO> itemDTOs = new ArrayList<>();

            for (MenuItem item : menu.getItems())
            {
                itemDTOs.add(toMenuItemDTO(item));
            }

            dto.setItems(itemDTOs);
        }

        return dto;
    }

    public static MenuItemDTO toMenuItemDTO(MenuItem menuItem)
    {
        MenuItemDTO dto = new MenuItemDTO();

        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setCreated(menuItem.getCreated());

        return dto;
    }

    public static ReservationRequestDTO toReservationRequestDTO(ReservationRequest reservationRequest)
    {
        ReservationRequestDTO dto = new ReservationRequestDTO();

        dto.setFirstName(reservationRequest.getFirstName());
        dto.setLastName(reservationRequest.getLastName());
        dto.setEmail(reservationRequest.getEmail());
        dto.setGroupSize(reservationRequest.getGroupSize());

        if(reservationRequest.getSeatingTime() != null)
        {
            dto.setSeatingId(reservationRequest.getSeatingTime().getId());

            if(reservationRequest.getSeatingTime().getEvent() != null)
            {
                dto.setEventId(reservationRequest.getSeatingTime().getEvent().getId());
            }
        }
        return dto;
    }
}
