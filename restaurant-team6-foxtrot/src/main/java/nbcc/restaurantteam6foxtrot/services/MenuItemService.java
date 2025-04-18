package nbcc.restaurantteam6foxtrot.services;

import nbcc.restaurantteam6foxtrot.repositories.MenuItemRepository;
import org.springframework.stereotype.Service;
import nbcc.restaurantteam6foxtrot.entities.MenuItem;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService
{
    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository)
    {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getAll()
    {
        return menuItemRepository.findAll();
    }

    public Optional<MenuItem> findById(long id)
    {
        return menuItemRepository.findById(id);
    }

    public MenuItem save(MenuItem menuItem)
    {
        return menuItemRepository.save(menuItem);
    }

    public void deleteById(long id)
    {
        menuItemRepository.deleteById(id);
    }
}
