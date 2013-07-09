package jt.beans;



import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// TODO: Auto-generated Javadoc
/**
 * Eine EntityManagerFactoryBean stellt der Applikation eine
 * EntityManagerFactory zur Verfügung. Da die EntityManagerFactory nicht
 * Serializable ist, kann sie nur in <b>transient</b> Eigenschaften injiziert
 * werden, falls die Bean, in die injiziert wird {@code @SessionScoped} ist.
 * 
 * @see Serializable
 * @author burghard.britzke
 */
@ApplicationScoped
public class EntityManagerFactoryBean implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
 
	/** The entity manager factory. */
	@Produces
	private EntityManagerFactory entityManagerFactory;

	/**
	 * Erzeugt eine neue EntityManagerFactoryBean.
	 */
	public EntityManagerFactoryBean() {
		entityManagerFactory = Persistence
				.createEntityManagerFactory("test");
	}

	/**
	 * Liefert die Eigenschaft entityManagerFactory.
	 * 
	 * @return the entityManager
	 */
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}
}
