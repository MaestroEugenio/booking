package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class User {
    /**
     * User Id. UNIQUE.
     * @return User Id.
     */
    private long id;
    private String name;
    private String email;

    public User(long id, String name, String email ){
        this.id = id;
        this.name = name;
        this.email = email;
    }
//    public long getId() {
//        return id;
//    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * User email. UNIQUE.
     * @return User email.
     */

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
