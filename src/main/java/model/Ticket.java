package model;

import javax.persistence.*;

@NamedQueries({
	@NamedQuery(
			name = "getUnreservedTicketsofStrecke",
			query = "SELECT t FROM Ticket t INNER JOIN t.strecke s WHERE s NOT IN (SELECT strecke FROM Reservierung) "
			+ "AND s.ende = :ende AND s.start = :start"
	)
})
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
