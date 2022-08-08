package config;

import application.BookingFacadeApplication;
import demo.DemoApplication;
import model.Event;
import model.Ticket;
import model.User;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

@SpringBootTest
public class BookingFacadeApplicationTest {

    AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
    BookingFacadeApplication booking = (BookingFacadeApplication) context.getBean("bookingConfig");

    private static Logger log = Logger.getLogger(DemoApplication.class);
    private static  final String LOG_FILE = "src/main/resources/log4j.properties";
    Properties properties = new Properties();


    private Properties getPropertiesForLog() throws IOException {
        properties.load(new FileInputStream(LOG_FILE));
        PropertyConfigurator.configure(properties);
        return properties;
    }

    @Test
    public void newUserTest(){
        User user = new User(333123, "oscar", "oscar@gmail.com");
        User userResult = booking.createUser(user);
        assertEquals(userResult, user);
    }

    @Test
    public void getUserTest(){
        booking.setUsers();
        long id = 3123;
        User userGot = booking.getUserById(id);
        assertEquals("Adam", userGot.getName());
    }

    @Test
    public void deleteUserTest(){
        booking.setUsers();
        long id = 3153;
        boolean deleted = booking.deleteUser(id);
        assertTrue(deleted);
    }

    @Test
    public void updateUserTest(){
        booking.setUsers();
        long id = 3133;
        User julia = booking.getUserById(id);
        //log
        User newUser = new User(id, "Damian", "damian@gmail.com"); //before was Julia
        assertEquals("Damian", newUser.getName());
    }

    @Test
    public void getUsersByNameTest(){
        booking.setUsers();
        User user = new User(4000, "Damian", "damian1@gmail.com");
        User user2 = new User(4001, "Damian", "damian2@gmail.com");
        User user3 = new User(4002, "Damian", "damian3@gmail.com");
        User user4 = new User(4003, "Damian", "damian4@gmail.com");
        booking.createUser(user);
        booking.createUser(user2);
        booking.createUser(user3);
        booking.createUser(user4);
        List<User> userExpected = new ArrayList<>();
        userExpected.add(user);
        userExpected.add(user2);
        userExpected.add(user3);
        userExpected.add(user4);
        List<User> userResult = booking.getUsersByName("Damian", 4, 4);
        assertEquals(userExpected, userResult);
    }

    @Test
    public void getUserByEmailTest(){
        booking.setUsers();
        String email = "julia@email.com";
        User userResult = booking.getUserByEmail(email);
        assertEquals(3133, userResult.getId());
    }

    @Test
    public void newEventTest(){
        Date date = new Date(2000, 12, 13);
        Event newEvent = new Event(2244324, "Event", date);
        Event eventResult = booking.createEvent(newEvent);
        assertEquals(eventResult, newEvent);
    }

    @Test
    public void getEventByIdTest(){
        booking.setEvents();
        long id = 16;
        Event eventResult = booking.getEventById(id);
        assertEquals("Event16", eventResult.getTitle());
    }

    @Test
    public void getEventByTitleTest(){
        booking.setEvents();
        Event newEvent = new Event(234, "Event15", new Date(1998, 11, 9));
        Event newEvent2 = new Event(236, "Event15", new Date(2001, 11, 9));
        booking.createEvent(newEvent);
        booking.createEvent(newEvent2);
        List<Event> eventsExpected = new ArrayList<>();
        eventsExpected.add(newEvent);
        eventsExpected.add(newEvent2);
        eventsExpected.add(booking.getEventById(15)); //Event included in preset data
        String title = "Event15";
        List<Event> eventsResult = booking.getEventsByTitle(title, 3, 3);
        assertEquals(eventsExpected, eventsResult);
    }

    @Test
    public void getEventsByDayTest(){
        booking.setEvents();
        Date date = new Date(2000, 12, 23);
        List<Event> eventsExpected = new ArrayList<>();

        //All of these events have same day
        eventsExpected.add(booking.getEventById(12));
        eventsExpected.add(booking.getEventById(13));
        eventsExpected.add(booking.getEventById(14));
        eventsExpected.add(booking.getEventById(15));
        eventsExpected.add(booking.getEventById(16));

        List<Event> eventsResult = booking.getEventsForDay(date, 5, 5);
        assertArrayEquals(eventsExpected.stream().toArray(), eventsResult.stream().toArray());
    }

    @Test
    public void updateEventTest(){
        booking.setEvents();
        Date date = new Date(2020, 11, 12);
        long id = 16;
        Event oldEvent = booking.getEventById(id);
        //log
        Event newEvent = new Event(id, "EventUpdated", date);
        Event oldEventResult = booking.updateEvent(newEvent);
        assertNotEquals(oldEventResult.getTitle(), newEvent.getTitle());
    }

    @Test
    public void newTicketTest(){
        booking.setEvents();
        booking.setUsers();
        long userId = 3133;
        long eventId = 14;
        Ticket ticketResult = booking.bookTicket(userId, eventId, 32, Ticket.Category.PREMIUM);
        assertEquals(ticketResult.getEventId(), eventId);
    }

    @Test
    public void getBookedTicketsUserTest(){
        booking.setUsers();
        booking.setEvents();
        booking.setTickets();
        long userId = 9877;
        long eventId = 14;
        User user = new User(userId, "Fer", "fer@email.com");
        booking.createUser(user);
        User userResult = booking.getUserById(userId);
        System.out.println(userResult.toString());

        Ticket ticketResult = booking.bookTicket(userId, eventId, 32, Ticket.Category.PREMIUM);
        Ticket ticketResult2 = booking.bookTicket(userId, eventId, 33, Ticket.Category.BAR);
        List<Ticket> ticketsExpected = new ArrayList<>();
        ticketsExpected.add(ticketResult);
        ticketsExpected.add(ticketResult2);
        List<Ticket> ticketsResult= booking.getBookedTickets(userResult, 2, 2);
        assertEquals(ticketsExpected, ticketsResult);
    }

    @Test
    public void getBookedTicketsEventTest(){
        booking.setUsers();
        booking.setEvents();
        booking.setTickets();
        long userId = 3143;
        long eventId = 98;
        Event event = new Event(eventId, "New Event", new Date(2022, 6, 13));
        booking.createEvent(event);

        //log

        Ticket ticketResult = booking.bookTicket(userId, eventId, 32, Ticket.Category.PREMIUM);
        Ticket ticketResult2 = booking.bookTicket(userId, eventId, 33, Ticket.Category.BAR);
        List<Ticket> ticketsExpected = new ArrayList<>();
        ticketsExpected.add(ticketResult);
        ticketsExpected.add(ticketResult2);
        List<Ticket> ticketsResult= booking.getBookedTickets(event, 2, 2);
        assertEquals(ticketsExpected, ticketsResult);
    }

    @Test
    public void cancelTicketTest(){
        booking.setTickets();
        long ticketId = 99299; //id in dataset
        boolean cancel = booking.cancelTicket(ticketId);
        assertTrue(cancel);
    }

    @Test
    public void realScenarioTest(){
        try {
            properties = getPropertiesForLog();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.debug("Creating user, event and ticket");
        User user = new User(99989, "Mateo", "mateo@gmail.com");
        log.debug(booking.createUser(user).toString());
        Event event = new Event(3333, "Event for Mateo", new Date(2022, 6, 13));
        log.debug(booking.createEvent(event).toString());
        Ticket ticketResult = booking.bookTicket(user.getId(), event.getId(), 89, Ticket.Category.PREMIUM);
        log.debug("Ticket created: "+ticketResult.toString());
        assertEquals(ticketResult.getEventId(), event.getId());
        assertEquals(ticketResult.getUserId(), user.getId());
        boolean canceled = booking.cancelTicket(ticketResult.getId());
        assertTrue(canceled);
    }

    @Test
    public void realScenarioTest2(){
        try {
            properties = getPropertiesForLog();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.debug("Creating user, event and ticket");
        User user = new User(99569, "Fernanda", "fernanda@email.com");
        log.debug(booking.createUser(user).toString());
        Event event = new Event(2133, "Event for Fernanda", new Date(2021, 8, 13));
        log.debug(booking.createEvent(event).toString());
        Ticket ticketResult = booking.bookTicket(user.getId(), event.getId(), 89, Ticket.Category.PREMIUM);
        log.debug("Ticket created: "+ticketResult.toString());
        assertEquals(ticketResult.getEventId(), event.getId());
        assertEquals(ticketResult.getUserId(), user.getId());
        boolean canceled = booking.cancelTicket(ticketResult.getId());
        assertTrue(canceled);
    }

    @Test
    public void realScenarioCannotCancelTest(){
        try {
            properties = getPropertiesForLog();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.debug("Creating user, event and ticket");
        User user = new User(99444, "Saul", "saul@email.com");
        log.debug(booking.createUser(user).toString());
        Event event = new Event(2164, "Event for Saul", new Date(2022, 3, 23));
        log.debug(booking.createEvent(event).toString());
        Ticket ticketResult = booking.bookTicket(user.getId(), event.getId(), 89, Ticket.Category.PREMIUM);
        log.debug("Ticket created: "+ticketResult.toString());
        assertEquals(ticketResult.getEventId(), event.getId());
        assertEquals(ticketResult.getUserId(), user.getId());
        boolean canceled = booking.cancelTicket(22111); //ticketId does not exist
        assertFalse(canceled);
    }
}
