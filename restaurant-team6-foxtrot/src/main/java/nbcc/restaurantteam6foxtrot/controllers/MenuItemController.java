package nbcc.restaurantteam6foxtrot.controllers;

import nbcc.restaurantteam6foxtrot.entities.MenuItem;
import nbcc.restaurantteam6foxtrot.services.MenuItemService;
import nbcc.restaurantteam6foxtrot.services.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menu/{menuId}/item")
public class MenuItemController
{
    private final MenuItemService menuItemService;
    private final MenuService menuService;

    public MenuItemController(MenuItemService menuItemService, MenuService menuService)
    {
        this.menuItemService = menuItemService;
        this.menuService = menuService;
    }

    @GetMapping("/edit/{itemId}")
    public String editForm(@PathVariable long menuId, @PathVariable long itemId, Model model)
    {
        var menuOpt = menuService.findById(menuId);
        var itemOpt = menuItemService.findById(itemId);

        if (menuOpt.isEmpty() || itemOpt.isEmpty())
        {
            return "redirect:/menu/" + menuId;
        }

        model.addAttribute("menu", menuOpt.get());
        model.addAttribute("menuItem", itemOpt.get());

        return "item/edit";
    }

    @PostMapping("/edit/{itemId}")
    public String edit(@PathVariable long menuId, @PathVariable long itemId, @ModelAttribute MenuItem menuItem)
    {
        var menuOpt = menuService.findById(menuId);

        if (menuOpt.isPresent())
        {
            menuItem.setId(itemId);
            menuItem.setMenu(menuOpt.get());

            menuItemService.save(menuItem);
        }

        return "redirect:/menu/" + menuId;
    }

    @GetMapping("/delete/{itemId}")
    public String delete(@PathVariable long menuId, @PathVariable long itemId)
    {
        menuItemService.deleteById(itemId);

        return "redirect:/menu/" + menuId;
    }
}

