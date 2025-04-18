package nbcc.restaurantteam6foxtrot.dtos;

public class ReservationRequestDTO
{
    private Long eventId;

    private Long seatingId;

    private String firstName;

    private String lastName;

    private String email;

    private int groupSize;

    public ReservationRequestDTO()
    {
    }

    public Long getEventId()
    {
        return eventId;
    }

    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
    }

    public Long getSeatingId()
    {
        return seatingId;
    }

    public void setSeatingId(Long seatingId)
    {
        this.seatingId = seatingId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getGroupSize()
    {
        return groupSize;
    }

    public void setGroupSize(int groupSize)
    {
        this.groupSize = groupSize;
    }
}
