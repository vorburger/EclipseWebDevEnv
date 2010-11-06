package ch.vorburger.demo.ext.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;


public class SomethingElseTest {

	@Test
	public void testSomething() throws Exception {
		assertThat(SomethingElse.message(), containsString("somethingElse, hallo"));
		assertThat(SomethingElse.message(), containsString("somethingCore"));
	}
}
