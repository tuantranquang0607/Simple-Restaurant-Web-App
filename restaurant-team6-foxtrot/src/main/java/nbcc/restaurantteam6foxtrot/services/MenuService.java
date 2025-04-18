package nbcc.restaurantteam6foxtrot.services;

import nbcc.restaurantteam6foxtrot.entities.Menu;
import nbcc.restaurantteam6foxtrot.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService
{
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository)
    {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAll()
    {
        return menuRepository.findAll();
    }

    public Optional<Menu> findById(long id)
    {
        return menuRepository.findById(id);
    }

    public Menu save(Menu menu)
    {
        return menuRepository.save(menu);
    }

    public void deleteById(long id)
    {
        menuRepository.deleteById(id);
    }
}
