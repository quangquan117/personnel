package personnel;

public class DateImpossible extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateImpossible()
	{
		super("La date d'arrivée doit étre inférieur à la date de départ");
		System.err.println("La date d'arrivée doit étre inférieur à la date de départ");
	}
}
