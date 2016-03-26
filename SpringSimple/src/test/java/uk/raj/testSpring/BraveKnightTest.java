package uk.raj.testSpring;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class BraveKnightTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testEmbarkOnQuest() {
		Quest questMock = mock(Quest.class);
		BraveKnight knight = new BraveKnight(questMock);
		knight.embarkOnQuest(questMock);
		Mockito.verify(questMock, Mockito.times(1)).embark();
		Mockito.verify(questMock, Mockito.atLeast(1)).embark();
	}

}
