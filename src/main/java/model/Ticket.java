package model;

import javax.persistence.*;

@Entity
public abstract class Ticket {
	@Id
	@GeneratedValue
	protected Long ID;

	@OneToOne
	protected Strecke strecke;
	
	@Embedded
	protected Zahlung zahlung;

	@Embedded
    private TicketOption DTYPE;
	
	public Ticket() {}
	
	public Ticket(Strecke strecke, Zahlung zahlung) {
		this.strecke = strecke;
		this.zahlung = zahlung;
	}

	public abstract String print();
}
