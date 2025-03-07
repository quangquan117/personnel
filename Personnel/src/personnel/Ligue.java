package personnel;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Représente une ligue. Chaque ligue est reliée é une liste
 * d'employés dont un administrateur. Comme il n'est pas possible
 * de créer un employé sans l'affecter é une ligue, le root est 
 * l'administrateur de la ligue jusqu'é ce qu'un administrateur 
 * lui ait été affecté avec la fonction {@link #setAdministrateur}.
 */

public class Ligue implements Serializable, Comparable<Ligue>
{
	private static final long serialVersionUID = 1L;
	private int id = -1;
	private String nom;
	private SortedSet<Employe> employes;
	private Employe administrateur;
	private GestionPersonnel gestionPersonnel;
	private LocalDate dateDepart;
	
	/**
	 * Crée une ligue.
	 * @param nom le nom de la ligue.
	 */
	
	Ligue(GestionPersonnel gestionPersonnel, String nom) throws SauvegardeImpossible
	{
		this(gestionPersonnel, -1, nom);
		this.id = gestionPersonnel.insert(this);
	}

	Ligue(GestionPersonnel gestionPersonnel, int id, String nom)
	{
		this.nom = nom;
		employes = new TreeSet<>();
		this.gestionPersonnel = gestionPersonnel;
		administrateur = gestionPersonnel.getRoot();
		this.id = id;
	}

	/**
	 * Retourne le nom de la ligue.
	 * @return le nom de la ligue.
	 */

	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom.
	 * @param nom le nouveau nom de la ligue.
	 */

	public void setNom(String nom)
	{
		this.nom = nom;
	}

	/**
	 * Retourne l'administrateur de la ligue.
	 * @return l'administrateur de la ligue.
	 */
	
	public Employe getAdministrateur()
	{
		return administrateur;
	}
	
	public int getId() {
		return this.id;
	}
	/**
	 * Fait de administrateur l'administrateur de la ligue.
	 * Léve DroitsInsuffisants si l'administrateur n'est pas 
	 * un employé de la ligue ou le root. Révoque les droits de l'ancien 
	 * administrateur.
	 * @param administrateur le nouvel administrateur de la ligue.
	 */
	
	public void setAdministrateur(Employe administrateur)
	{
		Employe root = gestionPersonnel.getRoot();
		if (administrateur != root && administrateur.getLigue() != this)
			throw new DroitsInsuffisants();
		this.administrateur = administrateur;
		
	}

	/**
	 * Retourne les employés de la ligue.
	 * @return les employés de la ligue dans l'ordre alphabétique.
	 */
	
	public SortedSet<Employe> getEmployes()
	{
		return Collections.unmodifiableSortedSet(employes);
		
	}

	/**
	 * Ajoute un employé dans la ligue. Cette méthode 
	 * est le seul moyen de créer un employé.
	 * @param nom le nom de l'employé.
	 * @param prenom le prénom de l'employé.
	 * @param mail l'adresse mail de l'employé.
	 * @param password le password de l'employé.
	 * @param string2 
	 * @param string 
	 * @return l'employé créé. 
	 * @throws SauvegardeImpossible 
	 */
	public Employe addEmploye(String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart, int id) {
        Employe employe = new Employe(this.gestionPersonnel, this, nom, prenom, mail, password, dateArrivee, dateDepart, id);
        employes.add(employe);
        return employe;
    }
	public Employe addEmploye(String nom, String prenom, String mail, String password, LocalDate date_arrivee,LocalDate date_depart) {
		Employe employe = new Employe(this.gestionPersonnel, this, nom, prenom, mail, password, date_arrivee, date_depart);
        employes.add(employe);
        return employe;
	}
	
	void remove(Employe employe)
	{
		employes.remove(employe);
	}
	/**
	 * Supprime la ligue, entraéne la suppression de tous les employés
	 * de la ligue.
	 * @throws SauvegardeImpossible 
	 */
	

	@Override
	public int compareTo(Ligue autre)
	{
		return getNom().compareTo(autre.getNom());
	}
	public void remove() throws SauvegardeImpossible
	{
		gestionPersonnel.remove(this);
	}
	public void removeAdmin()
	{
		administrateur = gestionPersonnel.getRoot();
	}
	public void update() throws SQLException
	{
		try { /* TODO pas d'entre sortie enléve try catch*/
			gestionPersonnel.update(this);
		} catch (SauvegardeImpossible e) {
			
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString()
	{
		return nom;
	}

}
