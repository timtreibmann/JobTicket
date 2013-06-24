package jt.entities;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-06-19T12:10:46.491+0200")
@StaticMetamodel(Angestellte.class)
public class Angestellte_ {
	public static volatile SingularAttribute<Angestellte, Integer> id;
	public static volatile SingularAttribute<Angestellte, String> nachname;
	public static volatile SingularAttribute<Angestellte, BigDecimal> stundenlohn;
	public static volatile SingularAttribute<Angestellte, String> vorname;
	public static volatile SingularAttribute<Angestellte, Angestelltenbezeichnungen> angestelltenbezeichnungen;
	public static volatile ListAttribute<Angestellte, Jobbearbeiter> jobbearbeiters;
	public static volatile ListAttribute<Angestellte, Kosten> kostens;
}
