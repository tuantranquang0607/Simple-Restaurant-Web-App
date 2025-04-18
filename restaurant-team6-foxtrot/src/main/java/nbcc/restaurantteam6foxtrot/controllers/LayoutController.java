package nbcc.restaurantteam6foxtrot.controllers;

import jakarta.validation.Valid;
import nbcc.restaurantteam6foxtrot.entities.DiningTable;
import nbcc.restaurantteam6foxtrot.entities.Event;
import nbcc.restaurantteam6foxtrot.entities.Layout;
import nbcc.restaurantteam6foxtrot.entities.SeatingTime;
import nbcc.restaurantteam6foxtrot.repositories.DiningTableRepository;
import nbcc.restaurantteam6foxtrot.repositories.EventRepository;
import nbcc.restaurantteam6foxtrot.repositories.LayoutRepository;
import nbcc.restaurantteam6foxtrot.repositories.ReservationRequestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/layouts")
public class LayoutController {
    private final LayoutRepository layoutRepository;
    private final DiningTableRepository diningTableRepository;
    private final EventRepository eventRepository;
    private final ReservationRequestRepository reservationRequestRepository;

    public LayoutController(LayoutRepository layoutRepository, DiningTableRepository diningTableRepository, EventRepository eventRepository, ReservationRequestRepository reservationRequestRepository) {
        this.layoutRepository = layoutRepository;
        this.diningTableRepository = diningTableRepository;
        this.eventRepository = eventRepository;
        this.reservationRequestRepository = reservationRequestRepository;
    }

    @GetMapping()
    public String getAll(Model model)
    {
        var values = layoutRepository.findByArchived(false);
        model.addAttribute("layouts", values);
        return "/layouts/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("layout", new Layout());
        return "/layouts/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("layout") Layout layout, BindingResult result) {
        if (result.hasErrors()) {
            return "/layouts/create";
        }

        layoutRepository.save(layout);
        return "redirect:/layouts";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id)
    {
        var entity = layoutRepository.findById(id);


        if (entity.isPresent())
        {
            Layout layout = entity.get();
            List<DiningTable> diningTables = diningTableRepository.findByArchivedNotAndLayout(true, layout);
            model.addAttribute("diningTable", new DiningTable());
            model.addAttribute("diningTables", diningTables);

            model.addAttribute("layout", layout);

            return "layouts/detail";
        }

        return "redirect:/layouts";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id)
    {
        var entity = layoutRepository.findById(id);

        if (entity.isPresent())
        {
            model.addAttribute("layout", entity.get());


            return "layouts/edit";
        }

        return "redirect:/event";
    }



    @PostMapping("/{id}/edit")
    public String edit(@PathVariable long id, @ModelAttribute Layout layout, BindingResult result)
    {
        var existingLayout = layoutRepository.findById(id);

        if (existingLayout.isPresent())
        {
            if (result.hasErrors()){
                return "/layouts/edit";
            }
            layout.setId(id);
            layout.setUpdatedAt(LocalDateTime.now());

            layoutRepository.save(layout);
        }

        return "redirect:/layouts/" + id;
    }

    @GetMapping("/{id}/remove")
    public String remove(Model model, @PathVariable long id)
    {
        var entity = layoutRepository.findById(id);


        if (entity.isPresent())
        {
            Layout layout = entity.get();
            List<DiningTable> diningTables = diningTableRepository.findAllByLayout(layout);
            model.addAttribute("diningTable", new DiningTable());
            model.addAttribute("diningTables", diningTables);
            //List<SeatingTime> seatings = seatingTimeRepository.findAllByEvent(event);

            model.addAttribute("layout", layout);
            //model.addAttribute("seatings", seatings);

            return "layouts/delete";
        }

        return "redirect:/layouts";
    }

    @PostMapping("/{id}/remove")
    public String remove(@PathVariable long id)
    {
        var existingLayout = layoutRepository.findById(id);

        if (existingLayout.isPresent()) {
            Layout layout = existingLayout.get();


            if (!eventRepository.findAllByLayout(layout).isEmpty()) {

                layout.setArchived(true);
                layoutRepository.save(layout);
            } else {

                layoutRepository.delete(layout);
            }
        }

        return "redirect:/layouts";
    }

    @PostMapping("/{id}/create")
    public String create(@PathVariable long id, @Valid @ModelAttribute("diningTable") DiningTable diningTable, BindingResult result) {

        var layout = layoutRepository.findById(id);
        layout.ifPresent(diningTable::setLayout);


        if (result.hasErrors()) {
            return "/layouts/detail";
        }

        diningTableRepository.save(diningTable);
        return "redirect:/layouts/" + id;
    }

    @PostMapping("/{layoutId}/remove/{id}")
    public String delete(@PathVariable long layoutId, @PathVariable long id) {
        var existingDiningTable = diningTableRepository.findById(id);

        existingDiningTable.ifPresent(diningTable -> {
            boolean hasReservations = reservationRequestRepository.existsByTable(diningTable);

            if (hasReservations) {
                diningTable.setArchived(true);
                diningTableRepository.save(diningTable);
            } else {

                diningTableRepository.deleteById(diningTable.getId());
            }
        });

        return "redirect:/layouts/" + layoutId;
    }
}