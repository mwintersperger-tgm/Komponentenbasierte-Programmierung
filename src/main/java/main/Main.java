package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import model.*;

// add hibernate
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
//import org.hibernate.query.Query;

import java.io.File;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

public class Main {

	private static final Logger log = Logger.getLogger(Main.class);

	private static EntityManagerFactory sessionFactory;
	@PersistenceContext
	private static EntityManager entitymanager;
	
	static SimpleDateFormat dateForm = new SimpleDateFormat("dd.MM.yyyy");
	static SimpleDateFormat timeForm = new SimpleDateFormat("dd.MM.yyyy mm:hh");

	private Main() {
		super();
	}

	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		System.out.println("###############################################");
		System.out.println("HALLO WESTBAHN");
		System.out.println("###############################################");
		try {

			// Stolen from HibernateUti(https://gist.github.com/yusufcakmak/215ede715bab0e1d6489)
			
			// loading hibernate.cfg.xml from root
			// Mit Hibernate
			//Configuration configuration = new Configuration();
			//configuration.configure().buildSessionFactory();
			
			//SessionFactory factory = configuration.buildSessionFactory();
			//Session entitymanager = factory.openSession();
			
			log.info("Starting \"Mapping Perstistent Classes and Associations\" (task1)");
			// Ohne Hibernate
			sessionFactory = Persistence.createEntityManagerFactory("westbahn");
			entitymanager = sessionFactory.createEntityManager();
			
			
			fillDB(entitymanager);
			task01();
			log.info("Starting \"Working with JPA-QL and the Hibernate Criteria API\" (task2)");
			log.setLevel(Level.OFF);
			task02();
			task02a();
			task02b();
			task02c();
			log.setLevel(Level.ALL);
			task03(entitymanager);
		} catch (ParseException e) {
			System.out.println("###############################################");
			System.out.println("Parse Exception");
			System.out.println("###############################################");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("###############################################");
			System.out.println("Exception");
			System.out.println("###############################################");
			e.printStackTrace();
		} finally {
/*			if (entitymanager.getTransaction().isActive())
				entitymanager.getTransaction().rollback();
			entitymanager.close();
			sessionFactory.close();
			*/
		}
		System.out.println("###############################################");
		System.out.println("BYE WESTBAHN");
		System.out.println("###############################################");
	}

	public static void fillDB(EntityManager em) throws ParseException {
		em.getTransaction().begin();
		
		System.out.println("###############################################");
		System.out.println("Fill Bahnhoefe");
		System.out.println("###############################################");
		// Bahnhöfe
		List<Bahnhof> list1 = new ArrayList<Bahnhof>();
		list1.add(new Bahnhof("WienHbf", 0, 0, 0, true));
		list1.add(new Bahnhof("SalzburgHbf", 20, 60, 120, true));
		list1.add(new Bahnhof("Amstetten", 40, 124, 169, false));
		list1.add(new Bahnhof("Linz-Ost", 140, 320, 250, false));
		list1.add(new Bahnhof("Huetteldorf", 3, 5, 19, false));
		list1.add(new Bahnhof("Wels-Zentrum", 102, 400, 250, true));
		for (Bahnhof bah : list1){
			em.persist(bah);
		}
		
		System.out.println("###############################################");
		System.out.println("Fill Strecken");
		System.out.println("###############################################");
		// Strecken
		List<Strecke> list2 = new ArrayList<Strecke>();
		list2.add(new Strecke(list1.get(0), list1.get(5), list1.get(1))); // Wien, Wels, Salzburg
		list2.add(new Strecke(list1.get(0), list1.get(2), list1.get(3))); // Wien, Amstetten, Linz
		list2.add(new Strecke(list1.get(0), list1.get(3), list1.get(1))); // Wien, Linz, Salzburg
		for (Strecke str : list2){
			em.persist(str);
		}
		
		System.out.println("###############################################");
		System.out.println("Fill Zug");
		System.out.println("###############################################");
		// Zug
		List<Zug> list3 = new ArrayList<Zug>();
		list3.add(new Zug(list1.get(0), list1.get(1), timeToDate(10, 5), 510, 50, 5)); // Wien, Salzburg
		list3.add(new Zug(list1.get(0), list1.get(5), timeToDate(20, 10), 450, 60, 25)); // Wien, Linz
		list3.add(new Zug(list1.get(2), list1.get(3), timeToDate(15, 0), 475, 50, 15)); // Amstetten, Linz
		for (Zug z : list3){
			em.persist(z);
		}

        Zahlung kredit = new Kreditkarte();
        Zahlung maestro = new Maestro();
        Zahlung praemienmeilen = new Praemienmeilen();
		
		System.out.println("###############################################");
		System.out.println("Fill Ticket");
		System.out.println("###############################################");
		// Ticket
		List<Ticket> list4 = new ArrayList<Ticket>();
        list4.add(new Zeitkarte(ZeitkartenTyp.WOCHENKARTE, new Date(), list2.get(0), kredit)); // Ticket to Salzburg
        list4.add(new Zeitkarte(ZeitkartenTyp.MONATSKARTE, new Date(2018, 3, Calendar.DAY_OF_MONTH+2), list2.get(2), praemienmeilen)); // Ticket zu Linz
		for (Ticket ti: list4){
			em.persist(ti);
		}
		
		System.out.println("###############################################");
		System.out.println("Fill Benutzer");
		System.out.println("###############################################");
		// Benutzer
		List<Benutzer> list5 = new ArrayList<Benutzer>();
        list5.add(new Benutzer("Michael", "Wintersperger", "mwintersperger@student.tgm.ac.at", "123Fiona", "06508831471", (long)(02), list4.get(1)));
        list5.add(new Benutzer("Thomas", "Wintersperger", "thomas.wintersperger@chello.at", "5chneeMa44", "06642279780", (long)(10), list4.get(0)));
		for (Benutzer ben: list5){
			em.persist(ben);
		}
		
		System.out.println("###############################################");
		System.out.println("Fill Reservierung");
		System.out.println("###############################################");
		// Reservierung
		List<Reservierung> list6 = new ArrayList<Reservierung>();
        list6.add(new Reservierung(futureDays(1), 15, 150, StatusInfo.ONTIME, list3.get(0), list2.get(0), list5.get(0), maestro));
        list6.add(new Reservierung(futureDays(4), 15, 150, StatusInfo.DELAYED, list3.get(1), list2.get(2), list5.get(1), kredit));
		for (Reservierung res: list6){
			em.persist(res);
		}

		em.flush();
		em.getTransaction().commit();
	}

	public static void task01() throws ParseException, InterruptedException {
		System.out.println("###############################################");
		System.out.println("Finished Task01");
		System.out.println("###############################################");
	}

	public static <T> void task02() throws ParseException {
		System.out.println("###############################################");
		System.out.println("Bahnhof Query");
		System.out.println("###############################################");

		Query q = entitymanager.createNamedQuery("Bahnhof.getAll");

		List<?> l = q.getResultList();

		for (Object b : l) {
			Bahnhof bhf = null;
			if (b instanceof Bahnhof) {
				bhf = (Bahnhof) b;
				System.out.println("Bahnhof: " + bhf.getName());
			}
		}
	}

	public static void task02a() throws ParseException {
		System.out.println("###############################################");
		System.out.println("Reservierung des Users Query");
		System.out.println("###############################################");
		
//		Query q = entitymanager.getNamedQuery("getReservationsFromMail");
		Query q = entitymanager.createNamedQuery("getReservationsFromMail");
        q.setParameter("eMail", "mwintersperger@student.tgm.ac.at");
 
		List<Reservierung> reservierungen = q.getResultList();
//      List<Reservierung> reservierungen = q.list();
        System.out.println("Records: "+reservierungen.size());
        for (Reservierung r : reservierungen){
            System.out.println(r.showReservierung());
		}
		System.out.println("###############################################");
		
	}

	public static void task02b() throws ParseException {
		System.out.println("###############################################");
		System.out.println("Benutzer mit Monatskarte Query");
		System.out.println("###############################################");
		
//		Query q = entitymanager.getNamedQuery("getUsersWithZeitkartentypMonatskarte");
		Query q = entitymanager.createNamedQuery("getUsersWithZeitkartentypMonatskarte");
 
		List<Benutzer> benutzer = q.getResultList();
//      List<Benutzer> benutzer = q.list();
        System.out.println("Benutzer: " + benutzer.size());
        for (Benutzer b : benutzer){
            System.out.println(b.getName());
		}
		System.out.println("###############################################");
		
	}

	public static void task02c() throws ParseException {
		System.out.println("###############################################");
		System.out.println("Tickets der Strecke ohne Reservierung");
		System.out.println("###############################################");
		
		Query q1 = entitymanager.createNamedQuery("getNamedBahnhof");
        q1.setParameter("name", "WienHbf");
 
		List<Bahnhof> start = q1.getResultList();
		System.out.println("###############################################");
		System.out.println(start.get(0).getName());
		System.out.println("###############################################");
		
		Query q2 = entitymanager.createNamedQuery("getNamedBahnhof");
        q2.setParameter("name", "SalzburgHbf");
 
		List<Bahnhof> ende = q2.getResultList();
		System.out.println("###############################################");
		System.out.println(ende.get(0).getName());
		System.out.println("###############################################");
		
		
//		Query q = entitymanager.getNamedQuery("getUnreservedTicketsofStrecke");
		Query q = entitymanager.createNamedQuery("getUnreservedTicketsofStrecke");
        q.setParameter("start", start.get(0)); // Wien
        q.setParameter("ende", ende.get(0)); // Salzburg
 
		List<Ticket> tickets = q.getResultList();
//      List<Ticket> tickets = q.list();
        System.out.println("Records: "+tickets.size());
        for (Ticket t : tickets){
            System.out.println(t.print());
		}
		System.out.println("###############################################");
		
	}

	public static void task03(EntityManager em) {
	}

	public static void validate(Object obj) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> violations = validator.validate(obj);
		for (ConstraintViolation<Object> violation : violations) {
			log.error(violation.getMessage());
			System.out.println(violation.getMessage());
		}
	}
	
	@SuppressWarnings("deprecation")
    public static Date futureDays(int days) {
        Date d = new Date();
        d.setDate(d.getDate()+days);
        return d;
    }

    @SuppressWarnings("deprecation")
    public static Date timeToDate(int hour, int min) {
        Date d = new Date();
        d.setHours(hour);
        d.setMinutes(min);
        return d;
    }

}