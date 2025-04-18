package nbcc.restaurantteam6foxtrot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Event
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    @NotNull(message = "startDate is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "endDate is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "price is required")
    @Min(value = 0, message = "price must be greater than or equal to 0")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserDetail owner;

    public UserDetail getOwner() {
        return owner;
    }

    public void setOwner(UserDetail owner) {
        this.owner = owner;
    }
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate = LocalDate.now();

    private boolean archived = false;

    public boolean isArchived()
    {
        return archived;
    }

    @ManyToOne
    @JoinColumn(name = "layout_id", nullable = false)
    private Layout layout;

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public void setArchived(boolean archived)
    {
        this.archived = archived;
    }

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatingTime> seatingTimes;

    public List<SeatingTime> getSeatingTimes()
    {
        return seatingTimes;
    }

    public void setSeatingTimes(List<SeatingTime> seatingTimes)
    {
        this.seatingTimes = seatingTimes;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Event() {}

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

    public void setPrice(double price)
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

    public boolean isSingleDayEvent() {
        return startDate.isEqual(endDate);
    }
}
