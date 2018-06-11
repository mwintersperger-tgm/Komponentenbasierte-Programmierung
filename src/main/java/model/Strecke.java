package model;

import javax.persistence.*;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;

@Entity
public class Strecke {
	@Id
	@GeneratedValue
	private Long ID;
	
	@NotNull
	@OneToOne
	private Bahnhof start;

	@OneToOne
	private Bahnhof bahnhof;

	@NotNull
	@OneToOne
	private Bahnhof ende;

	public Strecke() {}

	public Strecke(Bahnhof start, Bahnhof ende, Bahnhof bahnhof) {
		this.start = start;
		this.ende = ende;
		this.bahnhof = bahnhof;
		this.endeIsStart = this.ende.getName().equals(this.start.getName());
	}
	
	@AssertFalse(message="Start und Endstation müssen unterschiedliche Stationen sein")
	private boolean endeIsStart;
	
	public long getID(){
		return this.ID;
	}
	
	public String geStartbahnhof() {
		return start.getName();
	}
	
	public String getEndbahnhof() {
		return ende.getName();
	}
}
