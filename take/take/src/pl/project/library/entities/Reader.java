package pl.project.library.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = Reader.TABLENAME)
public class Reader implements Serializable {
    public final static String TABLENAME = "READERS";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID readerID;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false, unique = true)
    private String personalID;

    @OneToMany(mappedBy = "reader", fetch = FetchType.EAGER)
    private List<Lend> lends = new ArrayList<>();

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalID() {
        return personalID;
    }
    public void setPersonalID(String personalID) {
        this.personalID = personalID;
    }

    public List<Lend> getLends() {
        return lends;
    }
    public void setLends(List<Lend> lends) {
        this.lends = lends;
    }
}
