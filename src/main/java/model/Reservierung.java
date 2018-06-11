package model;

import java.util.Date;
// no java persistence import, weil xml mapped
public class Reservierung {
	private Long ID;
	private Date datum;
	private int praemienMeilenBonus = 15;
	private int preis = 150;
	private StatusInfo status;
	private Zug zug;
	private Strecke strecke;
	private Benutzer benutzer;
	private Zahlung zahlung;

	public Reservierung() {}

	public Reservierung(Date datum, int praemienMeilenBonus, int preis, StatusInfo status, Zug zug, Strecke strecke, Benutzer benutzer, Zahlung zahlung) {
		super();
		this.datum = datum;
		this.praemienMeilenBonus = praemienMeilenBonus;
		this.preis = preis;
		this.status = status;
		this.zug = zug;
		this.strecke = strecke;
		this.benutzer = benutzer;
		this.zahlung = zahlung;
	}
	public String showReservierung() {
		return benutzer.getName() + " hat eine Reservierung für einen zug nach: " + strecke.getEndbahnhof() + " am " + datum.toString();
	}
	
	public Benutzer getBenutzer() {
		return this.benutzer;
	}
}
