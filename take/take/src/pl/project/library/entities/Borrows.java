package pl.project.library.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Borrows {
	private List<Borrow> borrows = new ArrayList<Borrow>();

	public Borrows(List<Borrow> borrows) {
		super();
		this.borrows = borrows;
	}

	public Borrows() {}
	
	public List<Borrow> getBorrows() {
		return borrows;
	}

	public void setBorrows(List<Borrow> borrows) {
		this.borrows = borrows;
	}
}
