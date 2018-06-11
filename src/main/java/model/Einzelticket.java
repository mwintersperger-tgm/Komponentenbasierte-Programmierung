package model;

import javax.persistence.*;

@Entity
public class Einzelticket extends Ticket {
	@Enumerated(EnumType.ORDINAL)
	private TicketOption ticketOption;

	public Einzelticket() {}
	
	public Einzelticket(TicketOption ticketOption, Strecke strecke, Zahlung zahlung) {
		super(strecke, zahlung);
		this.ticketOption = ticketOption;
	}
	
	@Override
	public String print() {
		return "Ticket " +super.ID + " ist vom Typ: Einzelticket, mit folgender Option: " + ticketOption; 
	}

}
