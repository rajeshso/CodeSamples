package uk.raj.testSpring;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class BraveKnightSpringTest {

	@Autowired
	private BraveKnight knight = null;

	@Autowired
	private Quest quest = null;
	
	@Test
	public final void testEmbarkOnQuest() {
		assertNotNull("Constructor knight instance is null.", knight);
		knight.embarkOnQuest(quest);
	}

}
