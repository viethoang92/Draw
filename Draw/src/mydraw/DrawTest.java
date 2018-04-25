package mydraw;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class DrawTest {
	private Draw window;
	
	public DrawTest()
	{
		window = new Draw();
	}
	
	@Test
	public void TestSettersAndGetters() throws ColorException
	{
		window.setFGColor("blue");
		assertEquals("blue", window.getFGColor());
		assertEquals("white", window.getBGColor());
		window.setBGColor("black");
		assertEquals("black", window.getBGColor());
		window.setHeight(700);
		assertEquals(700, window.getHeight());
		window.setWidth(700);
		assertEquals(700, window.getWidth());
	}
	
	
}
