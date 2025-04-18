package nbcc.restaurantteam6foxtrot.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class MenuDTO
{
    private long id;

    private String name;

    private String description;

    private LocalDateTime created;

    private List<MenuItemDTO> items;

    public MenuDTO()
    {
    }

    public MenuDTO(long id, String name, String description, LocalDateTime created, List<MenuItemDTO> items)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.items = items;
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

    public List<MenuItemDTO> getItems()
    {
        return items;
    }

    public void setItems(List<MenuItemDTO> items)
    {
        this.items = items;
    }
}
