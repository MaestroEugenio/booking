package model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Created by maksym_govorischev.
 */

@Getter
@Setter
public class Event {
    /**
     * Event id. UNIQUE.
     * @return Event Id
     */
    private long id;
    private String title;

    private Date date;

    public Event(long id, String title, Date date){
        super();
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public Event(String all){
        String[] pairs = all.split(",");
        this.id = Long.parseLong(pairs[0].trim());
        this.title = pairs[1].trim();
        this.date = new Date(pairs[2].trim());
    }

    @Override
    @JsonValue
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
