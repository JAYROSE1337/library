package pl.project.library.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = Reader.TABLENAME)
@Table(name = Reader.TABLENAME)
@XmlRootElement
public class Reader implements Serializable {

	private static final long serialVersionUID = 8188100090825309806L;

	public final static String TABLENAME = "READERS";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int readerID;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false, unique = true)
    private String personalID;
    
    public void setReaderID(int id) {
    	this.readerID = id;
    }
    
    public int getReaderID() {
    	return readerID;
    }

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
}
