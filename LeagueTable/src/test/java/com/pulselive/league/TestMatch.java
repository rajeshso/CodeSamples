/**
 * 
 */
package com.pulselive.league;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Rajesh
 *
 */
public class TestMatch {
	
	Match m = null;
	@Before
	public void setup() {
		 m = new Match("A", "B", 100, 120);
	}

	/**
	 * Test method for {@link com.pulselive.league.Match#Match(java.lang.String, java.lang.String, int, int)}.
	 */
	@Test
	public final void testMatch() {
		assertThat(m).isNotNull();
	}

	/**
	 * Test method for {@link com.pulselive.league.Match#getHomeTeam()}.
	 */
	@Test
	public final void testGetHomeTeam() {
		assertThat(m.getHomeTeam()).isEqualTo("A");
	}

	/**
	 * Test method for {@link com.pulselive.league.Match#getAwayTeam()}.
	 */
	@Test
	public final void testGetAwayTeam() {
		assertThat(m.getAwayTeam()).isEqualTo("B");
	}

	/**
	 * Test method for {@link com.pulselive.league.Match#getHomeScore()}.
	 */
	@Ignore
	public final void testGetHomeScore() {
		assertThat(m.getHomeScore()).isEqualTo(100);
	}

	/**
	 * Test method for {@link com.pulselive.league.Match#getAwayScore()}.
	 */
	@Test
	public final void testGetAwayScore() {
		assertThat(m.getAwayScore()).isEqualTo(120);
	}

	/**
	 * Test method for {@link com.pulselive.league.Match#updateLeagueEntries(java.util.Map)}.
	 */
	@Test
	public final void testUpdateLeagueEntries() {
		Map<String, LeagueTableEntry> entries = new HashMap<>(2);
		LeagueTableEntry entry1 = new LeagueTableEntry("A");
		LeagueTableEntry entry2 = new LeagueTableEntry("B");
		entries.put("A", entry1);
		entries.put("B", entry2);
		m = new Match("A", "B", 10, 5);
		m.updateLeagueEntries(entries);
		//A
		assertThat(entry1.getTeamName()).isEqualTo("A");
		assertThat(entry1.getDrawn()).isEqualTo(0);
		assertThat(entry1.getGoalDifference()).isEqualTo(5);
		assertThat(entry1.getGoalsAgainst()).isEqualTo(5);
		assertThat(entry1.getGoalsFor()).isEqualTo(10);
		assertThat(entry1.getLost()).isEqualTo(0);
		assertThat(entry1.getPlayed()).isEqualTo(1);
		assertThat(entry1.getPoints()).isEqualTo(3);
		assertThat(entry1.getWon()).isEqualTo(1);
		//B
		assertThat(entry2.getTeamName()).isEqualTo("B");
		assertThat(entry2.getDrawn()).isEqualTo(0);
		assertThat(entry2.getGoalDifference()).isEqualTo(-5);
		assertThat(entry2.getGoalsAgainst()).isEqualTo(10);
		assertThat(entry2.getGoalsFor()).isEqualTo(5);
		assertThat(entry2.getLost()).isEqualTo(1);
		assertThat(entry2.getPlayed()).isEqualTo(1);
		assertThat(entry2.getPoints()).isEqualTo(0);
		assertThat(entry2.getWon()).isEqualTo(0);
	}
	
	@Test
	public final void testInvalidSameTeamMatch() {
		try {
			m = new Match("A", "A", 100, 120);
			fail("Exception expected here");
		}catch(InvalidInputException iie) {
			assertThat(iie.getMessage()).isEqualTo("The teams cannot play within themselves");
		}
	}
	@Test
	public final void testInvalidNullTeamNames() {
		try {
			m = new Match(null, "A", 100, 120);
			fail("Exception expected here");
		}catch(InvalidInputException iie) {
			assertThat(iie.getMessage()).isEqualTo("The team names are invalid");
		}
	}
	@Test
	public final void testInvalidEmptyTeamNames() {
		try {
			m = new Match("", "              ", 100, 120);
			fail("Exception expected here");
		}catch(InvalidInputException iie) {
			assertThat(iie.getMessage()).isEqualTo("The team names are invalid");
		}
	}
	@Test
	public final void testInvalidLongTeamNames() {
		try {
			m = new Match("A", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa", 100, 120);
			fail("Exception expected here");
		}catch(InvalidInputException iie) {
			assertThat(iie.getMessage()).isEqualTo("The team names are longer than the permitted length of 100");
		}
	}
	@Test
	public final void testInvalidScores() {
		try {
			m = new Match("A", "B", Integer.MAX_VALUE, 120);
			fail("Exception expected here");
		}catch(InvalidInputException iie) {
			assertThat(iie.getMessage()).isEqualTo("The team score is longer than the permitted max score of 536870911");
		}
	}	

}
