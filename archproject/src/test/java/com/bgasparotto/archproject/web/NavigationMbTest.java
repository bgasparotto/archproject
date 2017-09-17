package com.bgasparotto.archproject.web;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link NavigationMb}.
 * 
 * @author Bruno Gasparotto
 *
 */
public class NavigationMbTest {

	@Test
	public void shouldStartOnWelcomePage() throws Exception {
		NavigationMb mb = new NavigationMb();
		String page = mb.getPage();

		Assert.assertEquals("welcome.xhtml", page);
	}

	@Test
	public void shouldStartOnDefinedPage() throws Exception {
		String somewhere = "somewhere.xhtml";
		NavigationMb mb = new NavigationMb(somewhere);
		String page = mb.getPage();

		Assert.assertEquals(somewhere, page);
	}

	@Test
	public void shouldNav() throws Exception {
		NavigationMb mb = new NavigationMb();

		String somewhereElse = "somewhere-else.xhtml";
		mb.nav(somewhereElse);

		String page = mb.getPage();
		Assert.assertEquals(somewhereElse, page);
	}

	@Test
	public void shouldSetAndGetAnotherPage() throws Exception {
		String toBeSet = "to-be-set.xhtml";
		NavigationMb mb = new NavigationMb();

		String page = mb.getPage();
		Assert.assertNotEquals(toBeSet, page);

		mb.setPage(toBeSet);
		page = mb.getPage();
		Assert.assertEquals(toBeSet, page);
	}
}