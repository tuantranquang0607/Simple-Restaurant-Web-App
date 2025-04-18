package nbcc.restaurantteam6foxtrot.controllers.api;

import nbcc.restaurantteam6foxtrot.dtos.DTOConverters;
import nbcc.restaurantteam6foxtrot.dtos.MenuDTO;
import nbcc.restaurantteam6foxtrot.entities.Menu;
import nbcc.restaurantteam6foxtrot.services.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuApiController
{
    private final MenuService menuService;

    public MenuApiController(MenuService menuService)
    {
        this.menuService = menuService;
    }

    @GetMapping
    public List<MenuDTO> getAllMenus()
    {
        List<Menu> menus = menuService.getAll();
        return menus.stream().map(DTOConverters::toMenuDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> getMenuById(@PathVariable long id)
    {
        Optional<Menu> menuOpt = menuService.findById(id);

        if (menuOpt.isPresent())
        {
            MenuDTO dto = DTOConverters.toMenuDTO(menuOpt.get());

            return ResponseEntity.ok(dto);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
