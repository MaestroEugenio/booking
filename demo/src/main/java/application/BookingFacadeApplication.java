package application;

import com.fasterxml.jackson.databind.ObjectMapper;
import facade.BookingFacade;
import model.Event;
import model.Ticket;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class BookingFacadeApplication implements BookingFacade {


    private static HashMap<Long, Event> events = new HashMap<>();
    private static HashMap<Long, User> users = new HashMap<>();

    private static HashMap<Long, Ticket> tickets = new HashMap<>();

    List<Event> eventList = new ArrayList<>();
    private static long id = 20;
    private static long userId = 33311;
    private static long ticketId = 992;

    @Autowired
    private Event event;

    @Autowired
    private User user;

    @Autowired
    private Ticket ticket;

    public void setEvents(){
        Date date = new Date(2000, 12, 23);
        events.put(12L, new Event(12, "Event12", date));
        events.put(13L, new Event(13, "Event13", date));
        events.put(14L, new Event(14, "Event14", date));
        events.put(15L, new Event(15, "Event15", date));
        events.put(16L, new Event(16, "Event16", date));
    }

    public void setUsers(){
        users.put(3123L, new User(3123, "Adam", "adam@email.com"));
        users.put(3133L, new User(3133, "Julia", "julia@email.com"));
        users.put(3143L, new User(3143, "Baruc", "baruc@email.com"));
        users.put(3153L, new User(3153, "Luis", "luis@email.com"));
    }

    public void setTickets(){
        User user = new User(3123, "Adam", "adam@email.com");
        Event event = new Event(14, "Event14", new Date(2000, 12, 23));
        tickets.put(99299L, new Ticket(event, user, 99299, Ticket.Category.BAR, 32));
        tickets.put(99292L, new Ticket(event, user, 99292, Ticket.Category.BAR, 32));
        tickets.put(99293L, new Ticket(event, user, 99293, Ticket.Category.BAR, 32));
        tickets.put(99294L, new Ticket(event, user, 99294, Ticket.Category.BAR, 32));
    }

    @Override
    public Event getEventById(long eventId) {
        return events.get(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return events.values().stream().filter(e-> e.getTitle().equals(title)).collect(Collectors.toList());
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return events.values().stream().filter(e-> e.getDate().equals(day)).sorted(Comparator.comparing(Event::getId)).collect(Collectors.toList());
    }

    @Override
    public Event createEvent(Event event) {
        events.put(event.getId(), event);
        return event;
    }

    @Override
    public Event updateEvent(Event event) {
        return events.replace(event.getId(), event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        Event eventRemoved = events.get(eventId);
        return events.remove(eventId, eventRemoved);
    }

    @Override
    public User getUserById(long userId) {
        return users.get(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return users.values().stream().filter(u -> u.getEmail().equals(email)).collect(Collectors.toList()).get(0);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return users.values().stream().filter(u -> u.getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public User createUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        return users.replace(user.getId(), user);
    }

    @Override
    public boolean deleteUser(long userId) {
        User userDeleted = users.get(userId);
        return users.remove(userId, userDeleted);
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        long ticketId2 = ticketId++;
        Ticket newTicket = new Ticket(events.get(eventId), users.get(userId), ticketId2, category, place);
        tickets.put(ticketId2, newTicket);
        return newTicket;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return tickets.values().stream().filter(t -> t.getUserId()==user.getId()).collect(Collectors.toList());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return tickets.values().stream().filter(t -> t.getEventId()==event.getId()).collect(Collectors.toList());
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        Ticket ticket = tickets.get(ticketId);
        return tickets.remove(ticketId, ticket);
    }

    public void init(){
        System.out.println("Bean is going through init");
    }

    public void destroy(){
        System.out.println("Bean will be destroyed");
    }
}
