package edu.selab;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class AppTest {
	App app;

	@Before
	public void before() {
		app = new App();
	}

	@Test
	public void test() {
		assertEquals("Hello World!", App.main("Hello World!"));
	}
}
