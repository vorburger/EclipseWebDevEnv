package ch.vorburger.modudemo.core.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;

import ch.vorburger.demo.core.services.Something;


public class SomethingTest {

	@Test
	public void testSomething() throws Exception {
		assertThat(Something.message(), containsString("somethingCore"));
	}
}
