package com.bgasparotto.archproject.persistence.dbunit;

/**
 * {@code DbUnit} constants for database connection.
 * 
 * @author Bruno Gasparotto
 *
 */
public enum DbUnitParameters {

	/**
	 * Driver class.
	 */
	DRIVER_CLASS("org.postgresql.Driver"),

	/**
	 * Connection URL of the database.
	 */
	CONNECTION_URL("jdbc:postgresql://localhost:5432/archproject"),

	/**
	 * Username to be used to access the database.
	 * */
	USERNAME("postgres"),

	/**
	 * Password used by the {@link DbUnitParameters#USERNAME USERNAME}.
	 */
	PASSWORD("postgres"),

	/**
	 * Path of the {@code xml} file containing the data to be used on persitence
	 * tests by {@code DbUnit}.
	 */
	FLAX_XML_FILE("src/test/resources/DbUnit/dbunit-test-db.xml"),

	/**
	 * The persistence unit to be used on test context.
	 */
	PERSISTENCE_UNIT("archproject-local");

	private final String value;

	/**
	 * Constructor.
	 * 
	 * @param value
	 *            The constant value
	 */
	private DbUnitParameters(String value) {
		this.value = value;
	}

	/**
	 * The the value of this enum element.
	 * 
	 * @return Value of this enum element
	 */
	public String getValue() {
		return value;
	}
}