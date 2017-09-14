package com.bgasparotto.archproject.web;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public abstract class ArquillianTestCase extends SeleniumTestCase {

	@Deployment
	public static WebArchive createDeployment() {

		/* Create the war file according to build.gradle. */
		WebArchive war = ShrinkWrap.create(EmbeddedGradleImporter.class)
				.forThisProjectDirectory().importBuildOutput()
				.as(WebArchive.class);

		/* Add the abstract test classes to war. */
		war.addClasses(ArquillianTestCase.class, SeleniumTestCase.class);
 
		/* Add selenium and its transitive dependencies to war's lib. */
		String seleniumJava = "org.seleniumhq.selenium:selenium-java:3.4.0";
		MavenResolverSystem resolver = Maven.resolver();
		File[] seleniumFiles = resolver.resolve(seleniumJava).withTransitivity()
				.asFile();
		war.addAsLibraries(seleniumFiles);
		
		/* Add the XML dataset to manage the database during the tests. */
		war.addAsResource("DbUnit/dbunit-test-db.xml");
		
		return war;
	}
}