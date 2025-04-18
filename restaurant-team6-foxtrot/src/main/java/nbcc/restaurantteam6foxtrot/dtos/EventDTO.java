package nbcc.restaurantteam6foxtrot.dtos;

import java.time.LocalDate;
import java.util.List;

public class EventDTO
{
    private long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double price;

    private LocalDate createdDate;

    private boolean archived;

    private List<SeatingTimeDTO> seatingTimes;

    public EventDTO()
    {
    }

    public EventDTO(long id, String name, String description, LocalDate startDate, LocalDate endDate, Double price, LocalDate createdDate, boolean archived, List<SeatingTimeDTO> seatingTimes)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.createdDate = createdDate;
        this.archived = archived;
        this.seatingTimes = seatingTimes;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public LocalDate getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate)
    {
        this.createdDate = createdDate;
    }

    public boolean isArchived()
    {
        return archived;
    }

    public void setArchived(boolean archived)
    {
        this.archived = archived;
    }

    public List<SeatingTimeDTO> getSeatingTimes()
    {
        return seatingTimes;
    }

    public void setSeatingTimes(List<SeatingTimeDTO> seatingTimes)
    {
        this.seatingTimes = seatingTimes;
    }
}
