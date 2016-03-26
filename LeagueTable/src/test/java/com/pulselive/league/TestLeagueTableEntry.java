/**
 * 
 */
package com.pulselive.league;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Rajesh
 *
 */
public class TestLeagueTableEntry {
	LeagueTableEntry lte = null;
	@Before
	public void setup() {
		lte = new LeagueTableEntry("A", 1, 0, 1, 0, 10, 10, 0, 1);
	}

	/**
	 * Test method for {@link com.pulselive.league.LeagueTableEntry#addWonCount()}.
	 */
	@Test
	public final void testAddWonCount() {
		lte.addWonCount();
		assertThat(lte.getPlayed()).isEqualTo(2);
		assertThat(lte.getDrawn()).isEqualTo(1);
		assertThat(lte.getPoints()).isEqualTo(4);
		assertThat(lte.getWon()).isEqualTo(1);
	}

	/**
	 * Test method for {@link com.pulselive.league.LeagueTableEntry#addLostCount()}.
	 */
	@Test
	public final void testAddLostCount() {
		lte.addLostCount();
		assertThat(lte.getPlayed()).isEqualTo(2);
		assertThat(lte.getDrawn()).isEqualTo(1);
		assertThat(lte.getPoints()).isEqualTo(1);
		assertThat(lte.getWon()).isEqualTo(0);
		assertThat(lte.getLost()).isEqualTo(1);
	}

	/**
	 * Test method for {@link com.pulselive.league.LeagueTableEntry#addDrawnCount()}.
	 */
	@Test
	public final void testAddDrawnCount() {
		lte.addDrawnCount();
		assertThat(lte.getPlayed()).isEqualTo(2);
		assertThat(lte.getDrawn()).isEqualTo(2);
		assertThat(lte.getPoints()).isEqualTo(2);
		assertThat(lte.getWon()).isEqualTo(0);
	}

	/**
	 * Test method for {@link com.pulselive.league.LeagueTableEntry#updateGoals(int, boolean)}.
	 */
	@Test
	public final void testUpdateGoals() {
		lte.updateGoals(1, true);
		assertThat(lte.getPlayed()).isEqualTo(1);
		assertThat(lte.getDrawn()).isEqualTo(1);
		assertThat(lte.getPoints()).isEqualTo(1);
		assertThat(lte.getWon()).isEqualTo(0);
	}
	/**
	 * Test method for {@link com.pulselive.league.LeagueTableEntry#updateGoals(int, boolean)}.
	 */
	@Test
	public final void testUpdateGoalsAgainst() {
		lte.updateGoals(1, false);
		assertThat(lte.getPlayed()).isEqualTo(1);
		assertThat(lte.getDrawn()).isEqualTo(1);
		assertThat(lte.getPoints()).isEqualTo(1);
		assertThat(lte.getWon()).isEqualTo(0);
	}
}
