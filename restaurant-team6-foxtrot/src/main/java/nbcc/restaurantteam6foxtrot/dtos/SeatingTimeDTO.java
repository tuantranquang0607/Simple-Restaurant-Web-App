package nbcc.restaurantteam6foxtrot.dtos;

import java.time.LocalDateTime;

public class SeatingTimeDTO
{
    private long id;

    private LocalDateTime startDateTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public SeatingTimeDTO()
    {
    }

    public SeatingTimeDTO(long id, LocalDateTime startDateTime, LocalDateTime createdAt, LocalDateTime updatedAt)
    {
        this.id = id;
        this.startDateTime = startDateTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public LocalDateTime getStartDateTime()
    {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime)
    {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAt = updatedAt;
    }
}
