package entity;

public class Preisstaffelung {

	private static Long serialVersionUID;
	private float grossGepaeck = 1.02f;
	private float fahrrad = 1.05f;
	private int zeitkarteWoche = 8;
	private int zeitkarteMonat = 25;
	private int zeitkarteJahr = 250;
	private static Preisstaffelung instance = null;
	public static Preisstaffelung getInstance() {
		if(instance == null)
			instance = new Preisstaffelung();

		return instance;
	}

	public Preisstaffelung() {

	}

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(Long serialVersionUID) {
        Preisstaffelung.serialVersionUID = serialVersionUID;
    }

    public float getGrossGepaeck() {
        return grossGepaeck;
    }

    public void setGrossGepaeck(float grossGepaeck) {
        this.grossGepaeck = grossGepaeck;
    }

    public float getFahrrad() {
        return fahrrad;
    }

    public void setFahrrad(float fahrrad) {
        this.fahrrad = fahrrad;
    }

    public int getZeitkarteWoche() {
        return zeitkarteWoche;
    }

    public void setZeitkarteWoche(int zeitkarteWoche) {
        this.zeitkarteWoche = zeitkarteWoche;
    }

    public int getZeitkarteMonat() {
        return zeitkarteMonat;
    }

    public void setZeitkarteMonat(int zeitkarteMonat) {
        this.zeitkarteMonat = zeitkarteMonat;
    }

    public int getZeitkarteJahr() {
        return zeitkarteJahr;
    }

    public void setZeitkarteJahr(int zeitkarteJahr) {
        this.zeitkarteJahr = zeitkarteJahr;
    }

    public static void setInstance(Preisstaffelung instance) {
        Preisstaffelung.instance = instance;
    }
}
