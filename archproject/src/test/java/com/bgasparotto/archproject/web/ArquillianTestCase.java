package com.bgasparotto.archproject.web;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ArquillianTestCase {

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(EmbeddedGradleImporter.class)
				.forThisProjectDirectory().importBuildOutput()
				.as(WebArchive.class);
	}

	@Test
	@Ignore
	public void shouldRun() throws Exception {
		System.out.println("Arquillian!");
	}
}