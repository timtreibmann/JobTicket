/**
 * 
 */
package jt.beans;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * Diese Bean ist für die Verbindung zum LDAP-Server zuständig,
 * auf welchem die Benutzer als ActiveDirectory angelegt sind.
 * Über die Bean kann außerdem nach Usern gesucht werden.
 * @author marcus
 */

@RequestScoped
@Named
public class LDAPBean {

	private DirContext ldapContex;
	
	/**
	 * Verbindet sich mit dem Server.
	 */
	public LDAPBean() {
		ldapConnect();
	}
	
	
	/**
	 * Erstellt eine Verbindung zum Server und initialisiert die Eigenschaft ldapContext(DirContext).
	 */
	private void ldapConnect() {
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://runzefilem.rc.intra:389");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, "Administrator@rc.intra");
			env.put(Context.SECURITY_CREDENTIALS, "28072Kinder");
			ldapContex = new InitialDirContext(env);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Suchfunktion um Mitarbeiter in Form einer Liste von 'Attributes' aus dem
	 * LDAP Server zu lesen. Wobei die Attribute 'givenName', 'sn' und
	 * 'sAMAccountName' aus dem LDAP gespeichert werden.
	 * 
	 * @param filter
	 *            String um nach bestimmten Attributen in Form von
	 *            "sn=Musterman" zu suchen, null or "" um alle Mitarbeiter zu
	 *            bekommen
	 */
	public List<Attributes> searchLDAP(String filter) throws NamingException {
		List<Attributes> allAttrs = new ArrayList<Attributes>();
		try {
			SearchControls searchCtls = new SearchControls();
			String returnedAtts[] = { "givenName", "sn", "sAMAccountName" };
			searchCtls.setReturningAttributes(returnedAtts);
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			String searchFilter = "(&(objectClass=user)(sn=*))";
			String searchBase = "DC=rc,DC=intra";
			if (filter != null && !filter.equals("")) {
				searchFilter = "(&(objectClass=user)(" + filter + "))";
			}

			NamingEnumeration<SearchResult> answer = ldapContex.search(
					searchBase, searchFilter, searchCtls);
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();

				Attributes attrs = sr.getAttributes();
				if (attrs != null
						&& (attrs.get("givenName") != null
								&& attrs.get("sn") != null && attrs
								.get("sAMAccountName") != null)) {
					allAttrs.add(attrs);
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return allAttrs;
	}


	/**
	 * @return the ldapContex
	 */
	public DirContext getLdapContex() {
		return ldapContex;
	}


	/**
	 * @param ldapContex the ldapContex to set
	 */
	public void setLdapContex(DirContext ldapContex) {
		this.ldapContex = ldapContex;
	}

}
