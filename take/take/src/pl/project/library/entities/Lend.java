package pl.project.library.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.io.Serializable;
import java.util.Date;

@Entity(name = Lend.TABLENAME)
@Table(name=Lend.TABLENAME)
@XmlRootElement
public class Lend implements Serializable{

	private static final long serialVersionUID = 9211071050967433288L;

	public static final String TABLENAME = "LENDS";
	
	@Id
	@GeneratedValue(strategy= GenerationType.TABLE)
	private int lendID;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lendDate;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOKCOPIES_copyID", referencedColumnName = "copyID")
    @XmlTransient
    private BookCopy copy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "READERS_readerID", referencedColumnName = "readerID")
    @XmlTransient
    private Reader reader;

    public void setLendID(int id) {
    	this.lendID = id;
    }
    public int getLendID() { return this.lendID; }

    public Date getLendDate() {return this.lendDate;}
    public void setLendDate(Date lendDate) {this.lendDate = lendDate;}

    public Date getReturnDate() {return this.returnDate;}
    public void setReturnDate(Date returnDate) {this.returnDate = returnDate;}

    public BookCopy getBorrowedCopy() {return this.copy;}
    public void setBorrowedCopy(BookCopy copy) {this.copy = copy;}
    
    public Reader getReader() {return this.reader;}
    public void setReader(Reader reader) {
    	this.reader = reader;
    }


}
