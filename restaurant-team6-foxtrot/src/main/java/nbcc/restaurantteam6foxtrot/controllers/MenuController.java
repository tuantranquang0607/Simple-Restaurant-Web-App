package nbcc.restaurantteam6foxtrot.controllers;

import nbcc.restaurantteam6foxtrot.entities.Menu;
import nbcc.restaurantteam6foxtrot.entities.MenuItem;
import nbcc.restaurantteam6foxtrot.services.MenuItemService;
import nbcc.restaurantteam6foxtrot.services.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController
{
    private final MenuService menuService;
    private final MenuItemService menuItemService;

    public MenuController(MenuService menuService, MenuItemService menuItemService)
    {
        this.menuService = menuService;
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public String list(Model model)
    {
        List<Menu> menus = menuService.getAll();

        model.addAttribute("menus", menus);

        return "menu/index";
    }

    @GetMapping("/create")
    public String createForm(Model model)
    {
        model.addAttribute("menu", new Menu());

        return "menu/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Menu menu)
    {
        menuService.save(menu);

        return "redirect:/menu";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable long id, Model model)
    {
        var menuOpt = menuService.findById(id);

        if (menuOpt.isEmpty())
        {
            return "redirect:/menu";
        }

        Menu menu = menuOpt.get();
        MenuItem menuItem = new MenuItem();

        model.addAttribute("menu", menu);
        model.addAttribute("menuItem", menuItem);

        return "menu/detail";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable long id, Model model)
    {
        var menuOpt = menuService.findById(id);

        if (menuOpt.isEmpty())
        {
            return "redirect:/menu";
        }

        model.addAttribute("menu", menuOpt.get());

        return "menu/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable long id, @ModelAttribute Menu menu)
    {
        menu.setId(id);

        menuService.save(menu);

        return "redirect:/menu";
    }

    @GetMapping("/delete/{id}")
    public String confirmDelete(@PathVariable long id, Model model)
    {
        var menuOpt = menuService.findById(id);

        if (menuOpt.isEmpty())
        {
            return "redirect:/menu";
        }
        model.addAttribute("menu", menuOpt.get());
        return "menu/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable long id)
    {
        menuService.deleteById(id);

        return "redirect:/menu";
    }

    @PostMapping("/{id}/item")
    public String addItem(@PathVariable("id") long menuId, @ModelAttribute MenuItem menuItem)
    {
        menuItem.setId(0);

        var menuOpt = menuService.findById(menuId);

        if (menuOpt.isEmpty())
        {
            return "redirect:/menu";
        }

        Menu menu = menuOpt.get();
        menuItem.setMenu(menu);

        menuItemService.save(menuItem);

        return "redirect:/menu/" + menuId;
    }

}
