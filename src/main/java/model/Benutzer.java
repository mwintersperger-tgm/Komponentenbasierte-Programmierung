package model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;


@Entity
public class Benutzer implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;

	private String vorName;

	private String nachName;

	@Column(unique = true)
	@Pattern(regexp="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",
			 message = "This E-Mail must not be wrong!")
	private String eMail;

	@Column()
	private String passwort;

	@Column(unique = true)
	private String smsNummer;

	private Long verbuchtePraemienMeilen;

	@OneToOne(cascade = CascadeType.ALL)
	private Ticket tickets;
	
	public Benutzer() {
		
	}

	public Benutzer(String vorName, String nachName, String eMail, String passwort, String smsNummer, Long verbuchtePraemienMeilen, Ticket tickets) {
		super();
		this.vorName = vorName;
		this.nachName = nachName;
		this.eMail = eMail;
		this.passwort = passwort;
		this.smsNummer = smsNummer;
		this.verbuchtePraemienMeilen = verbuchtePraemienMeilen;
		this.tickets = tickets;
	}

	public String getUser() {
		if (this.tickets.getClass().equals(model.Zeitkarte.class)){
			return this.vorName + " " + this.nachName + " (" + this.eMail + ") besitzt eine "+ ((Zeitkarte) this.tickets).getTyp();
		} else {
			return this.getName() + " (" + this.eMail + ") besitzt ein Einzelticket";
		}
	}
	
	public String getName() {
		return vorName + " " + nachName;
	}
	
	public void setName(String vorName, String nachName){
		this.vorName=vorName;
		this.nachName=nachName;
	}
	
	public String getEMail() {
		return this.eMail;
	}
	
	public void setEMail(String eMail){
		this.eMail=eMail;
	}
}
