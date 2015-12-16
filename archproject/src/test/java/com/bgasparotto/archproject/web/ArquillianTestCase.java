package com.bgasparotto.archproject.web;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public abstract class ArquillianTestCase extends SeleniumTestCase {

	@Deployment
	public static WebArchive createDeployment() {

		/* Create the war file according to build.gradle. */
		WebArchive war = ShrinkWrap.create(EmbeddedGradleImporter.class)
				.forThisProjectDirectory().importBuildOutput()
				.as(WebArchive.class);

		war.addClasses(ArquillianTestCase.class, SeleniumTestCase.class);
		
		// FIXME Add Selenium and its transitive dependencies to war file. */
		
		return war;
	}

	@Test
	public void shouldRunArquillian() throws Exception {
		System.out.println("Arquillian!");
	}
}