package nbcc.restaurantteam6foxtrot.dtos;

import java.time.LocalDateTime;

public class MenuItemDTO
{
    private long id;

    private String name;

    private String description;

    private LocalDateTime created;

    public MenuItemDTO()
    {
    }

    public MenuItemDTO(long id, String name, String description, LocalDateTime created)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
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

    public LocalDateTime getCreated()
    {
        return created;
    }

    public void setCreated(LocalDateTime created)
    {
        this.created = created;
    }
}
