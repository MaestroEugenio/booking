package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by maksym_govorischev.
 */
@ToString
@Getter
@Setter
public class Ticket {
    public enum Category {STANDARD, PREMIUM, BAR}

    /**
     * Ticket Id. UNIQUE.
     * @return Ticket Id.
     */
    private final Event eventDAO;
    private final User user;
    private long id;
    private Category category;
    private int place;

    public Ticket(Event eventDAO, User userService, long ticketId, Category categoryT, int place){
        this.id = ticketId;
        this.user = userService;
        this.eventDAO = eventDAO;
        this.category = categoryT;
        this.place = place;
    }

    public long getEventId() {

        return eventDAO.getId();
    }

    public void setEventId(long eventId) {

        this.eventDAO.setId(eventId);
    }

    public long getUserId() {
        return user.getId();
    }

    public void setUserId(long userId) {

    }

}
