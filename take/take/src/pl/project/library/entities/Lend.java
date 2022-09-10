package pl.project.library.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name=Lend.TABLENAME)
public class Lend implements Serializable{
	static final String TABLENAME = "LENDS";
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private UUID lendID;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lendDate;

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKCOPIES_copyID", referencedColumnName = "copyID")
    private BookCopy copy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "READERS_readerID", referencedColumnName = "readerID")
    private Reader reader;

    public UUID getLendID() { return this.lendID; }

    public Date getLendDate() {return this.lendDate;}
    public void setLendDate(Date lendDate) {this.lendDate = lendDate;}

    public Date getReturnDate() {return this.returnDate;}
    public void setReturnDate(Date returnDate) {this.returnDate = returnDate;}

    public BookCopy getBorrowedCopy() {return this.copy;}
    public void setBorrowedCopy(BookCopy copy) {this.copy = copy;}

    public Reader getReader() {return this.reader;}


}
