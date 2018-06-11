package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Zeitkarte extends Ticket implements Serializable {
	private Date gueltigAb;

	private ZeitkartenTyp typ;
	
	public Zeitkarte() {}
	
	public Zeitkarte(ZeitkartenTyp typ, Date gueltigAb, Strecke strecke, Zahlung zahlung) {
		super(strecke,zahlung);
		this.typ = typ;
		this.gueltigAb = gueltigAb;
	}

	public ZeitkartenTyp getTyp() {
		return typ;
	}
	public void setTyp(ZeitkartenTyp typ) {
	    this.typ = typ;
	}

	@Override
	public String print() {
		return "Ticket mit der ID " + super.ID + " ist gültig ab " + gueltigAb.toString() + " und ist vom Typ " + typ;
	}

}
