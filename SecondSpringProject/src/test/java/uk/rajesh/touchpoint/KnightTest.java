package uk.rajesh.touchpoint;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.rajesh.touchpointImpl.BraveKnight;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/beans.xml"})
public class KnightTest {

	@Autowired
	private ApplicationContext context;
	
	@Test
	public final void testBraveKnightQuest() {
		BraveKnight knight = (BraveKnight) context.getBean("knight");
		assertNotNull(knight.getQuest());
	}

	@Test
	public final void testEmbarkOnQuest() {
		BraveKnight knight = (BraveKnight) context.getBean("knight");
		knight.embarkOnQuest();
	}

}
