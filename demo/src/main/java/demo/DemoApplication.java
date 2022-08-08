package demo;

import application.BookingFacadeApplication;
import model.Event;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class DemoApplication {

	/*Testing bean and shutdownhook*/
	private static Logger log = Logger.getLogger(DemoApplication.class);
	private static  final String LOG_FILE = "demo/src/main/resources/log4j.properties";

	public static void main(String[] args) {

		Properties properties = new Properties();

		AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

		BookingFacadeApplication booking = (BookingFacadeApplication) context.getBean("bookingConfig");

		booking.setEvents();
		booking.setUsers();
		booking.setTickets();

		try {

			properties.load(new FileInputStream(LOG_FILE));
			PropertyConfigurator.configure(properties);
			Event eventResult = booking.getEventById(16);
			//System.out.println(eventResult.toString());
			log.debug(eventResult.toString());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		context.registerShutdownHook();

	}

}
