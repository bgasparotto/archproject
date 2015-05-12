package com.bgasparotto.archproject.persistence.dbunit;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;

/**
 * Abstract test case class for {@code DbUnit}. This class should be extended
 * from classes willing to perform persistence tests.
 * 
 * @author Bruno Gasparotto
 *
 */
public abstract class DbUnitTestCase extends DBTestCase {

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * <p>
	 * The needed system properties for DbUnit test operations are configured by
	 * this constructor.
	 * </p>
	 */
	public DbUnitTestCase() {
		System.setProperty(
				PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
				DbUnitParameters.DRIVER_CLASS.getValue());
		System.setProperty(
				PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
				DbUnitParameters.CONNECTION_URL.getValue());
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
				DbUnitParameters.USERNAME.getValue());
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
				DbUnitParameters.PASSWORD.getValue());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		FileInputStream fis = new FileInputStream(
				DbUnitParameters.FLAX_XML_FILE.getValue());

		builder.setColumnSensing(true);
		FlatXmlDataSet dataSet = builder.build(fis);
		return dataSet;
	}

	@Override
	protected void setUpDatabaseConfig(DatabaseConfig config) {

		/*
		 * Enable fully qualified names usage for tables. This way it's possible
		 * to use <schema.table> tags on the flat xml file.
		 */
		config.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);

		/* Set the datatype for H2. */
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new H2DataTypeFactory());
	}
}