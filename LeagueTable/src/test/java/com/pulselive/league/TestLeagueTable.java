package com.pulselive.league;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestLeagueTable {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public final void testLeagueTableConstructor() {
		List<Match> matches = getSameTeamMatches();
		LeagueTable lt = new LeagueTable(matches);
		assertThat(lt.getMatches()).containsExactly((Match[]) matches.toArray());
	}
	
	@Test
	public final void testGetTableEntries() {
		List<Match> matches = getSameTeamMatches();
		LeagueTable lt = new LeagueTable(matches);
		List<LeagueTableEntry> tableEntries = lt.getTableEntries();
		assertThat(tableEntries.size()).isEqualTo(2);
		//A
		assertThat(tableEntries.get(0).getTeamName()).isEqualTo("A");
		assertThat(tableEntries.get(0).getGoalsFor()).isEqualTo(30);
		assertThat(tableEntries.get(0).getGoalsAgainst()).isEqualTo(15);
		assertThat(tableEntries.get(0).getDrawn()).isEqualTo(0);
		assertThat(tableEntries.get(0).getGoalDifference()).isEqualTo(15);
		assertThat(tableEntries.get(0).getGoalsAgainst()).isEqualTo(15);
		assertThat(tableEntries.get(0).getLost()).isEqualTo(0);
		assertThat(tableEntries.get(0).getPlayed()).isEqualTo(3);
		assertThat(tableEntries.get(0).getPoints()).isEqualTo(9);
		assertThat(tableEntries.get(0).getWon()).isEqualTo(3); 
		
		//B
		assertThat(tableEntries.get(1).getTeamName()).isEqualTo("B");
		assertThat(tableEntries.get(1).getGoalsFor()).isEqualTo(15);
		assertThat(tableEntries.get(1).getGoalsAgainst()).isEqualTo(30);
		assertThat(tableEntries.get(1).getDrawn()).isEqualTo(0);
		assertThat(tableEntries.get(1).getGoalDifference()).isEqualTo(-15);
		assertThat(tableEntries.get(1).getGoalsAgainst()).isEqualTo(30);
		assertThat(tableEntries.get(1).getLost()).isEqualTo(3);
		assertThat(tableEntries.get(1).getPlayed()).isEqualTo(3);
		assertThat(tableEntries.get(1).getPoints()).isEqualTo(0);
		assertThat(tableEntries.get(1).getWon()).isEqualTo(0);
	}
	@Test
	public final void testGetTableEntriesWithRandomTeamOrder() {
		List<Match> matches = getSameTeamMatchesWithRandomTeamOrder();
		LeagueTable lt = new LeagueTable(matches);
		List<LeagueTableEntry> tableEntries = lt.getTableEntries();
		assertThat(tableEntries.size()).isEqualTo(2);
		//A
		assertThat(tableEntries.get(0).getTeamName()).isEqualTo("A");
		assertThat(tableEntries.get(0).getGoalsFor()).isEqualTo(30);
		assertThat(tableEntries.get(0).getGoalsAgainst()).isEqualTo(15);
		assertThat(tableEntries.get(0).getDrawn()).isEqualTo(0);
		assertThat(tableEntries.get(0).getGoalDifference()).isEqualTo(15);
		assertThat(tableEntries.get(0).getGoalsAgainst()).isEqualTo(15);
		assertThat(tableEntries.get(0).getLost()).isEqualTo(0);
		assertThat(tableEntries.get(0).getPlayed()).isEqualTo(3);
		assertThat(tableEntries.get(0).getPoints()).isEqualTo(9);
		assertThat(tableEntries.get(0).getWon()).isEqualTo(3); 
		
		//B
		assertThat(tableEntries.get(1).getTeamName()).isEqualTo("B");
		assertThat(tableEntries.get(1).getGoalsFor()).isEqualTo(15);
		assertThat(tableEntries.get(1).getGoalsAgainst()).isEqualTo(30);
		assertThat(tableEntries.get(1).getDrawn()).isEqualTo(0);
		assertThat(tableEntries.get(1).getGoalDifference()).isEqualTo(-15);
		assertThat(tableEntries.get(1).getGoalsAgainst()).isEqualTo(30);
		assertThat(tableEntries.get(1).getLost()).isEqualTo(3);
		assertThat(tableEntries.get(1).getPlayed()).isEqualTo(3);
		assertThat(tableEntries.get(1).getPoints()).isEqualTo(0);
		assertThat(tableEntries.get(1).getWon()).isEqualTo(0);
	}
	
	@Test
	public final void testGetTableEntriesWithSingleMatch() {
		List<Match> matches = getSingleMatch();
		LeagueTable lt = new LeagueTable(matches);
		List<LeagueTableEntry> tableEntries = lt.getTableEntries();
		assertThat(tableEntries.size()).isEqualTo(2);
		//A
		assertThat(tableEntries.get(0).getTeamName()).isEqualTo("A");
		assertThat(tableEntries.get(0).getGoalsFor()).isEqualTo(10);
		assertThat(tableEntries.get(0).getGoalsAgainst()).isEqualTo(5);
		assertThat(tableEntries.get(0).getDrawn()).isEqualTo(0);
		assertThat(tableEntries.get(0).getGoalDifference()).isEqualTo(5);
		assertThat(tableEntries.get(0).getGoalsAgainst()).isEqualTo(5);
		assertThat(tableEntries.get(0).getLost()).isEqualTo(0);
		assertThat(tableEntries.get(0).getPlayed()).isEqualTo(1);
		assertThat(tableEntries.get(0).getPoints()).isEqualTo(3);
		assertThat(tableEntries.get(0).getWon()).isEqualTo(1); 
		
		//B
		assertThat(tableEntries.get(1).getTeamName()).isEqualTo("B");
		assertThat(tableEntries.get(1).getGoalsFor()).isEqualTo(5);
		assertThat(tableEntries.get(1).getGoalsAgainst()).isEqualTo(10);
		assertThat(tableEntries.get(1).getDrawn()).isEqualTo(0);
		assertThat(tableEntries.get(1).getGoalDifference()).isEqualTo(-5);
		assertThat(tableEntries.get(1).getGoalsAgainst()).isEqualTo(10);
		assertThat(tableEntries.get(1).getLost()).isEqualTo(1);
		assertThat(tableEntries.get(1).getPlayed()).isEqualTo(1);
		assertThat(tableEntries.get(1).getPoints()).isEqualTo(0);
		assertThat(tableEntries.get(1).getWon()).isEqualTo(0);
	}
	@Test
	public final void testUniqueTeamMatches() {
		List<Match> matches = getTwoDifferentMatches();
		LeagueTable lt = new LeagueTable(matches);
		List<LeagueTableEntry> tableEntries = lt.getTableEntries();
		assertThat(tableEntries.size()).isEqualTo(4);
		
		//A
		assertThat(tableEntries.get(0).getTeamName()).isEqualTo("A");
		assertThat(tableEntries.get(0).getGoalsFor()).isEqualTo(10);
		assertThat(tableEntries.get(0).getGoalsAgainst()).isEqualTo(5);
		assertThat(tableEntries.get(0).getDrawn()).isEqualTo(0);
		assertThat(tableEntries.get(0).getGoalDifference()).isEqualTo(5);
		assertThat(tableEntries.get(0).getGoalsAgainst()).isEqualTo(5);
		assertThat(tableEntries.get(0).getLost()).isEqualTo(0);
		assertThat(tableEntries.get(0).getPlayed()).isEqualTo(1);
		assertThat(tableEntries.get(0).getPoints()).isEqualTo(3);
		assertThat(tableEntries.get(0).getWon()).isEqualTo(1); 
		
		//B
		assertThat(tableEntries.get(1).getTeamName()).isEqualTo("B");
		assertThat(tableEntries.get(1).getGoalsFor()).isEqualTo(5);
		assertThat(tableEntries.get(1).getGoalsAgainst()).isEqualTo(10);
		assertThat(tableEntries.get(1).getDrawn()).isEqualTo(0);
		assertThat(tableEntries.get(1).getGoalDifference()).isEqualTo(-5);
		assertThat(tableEntries.get(1).getGoalsAgainst()).isEqualTo(10);
		assertThat(tableEntries.get(1).getLost()).isEqualTo(1);
		assertThat(tableEntries.get(1).getPlayed()).isEqualTo(1);
		assertThat(tableEntries.get(1).getPoints()).isEqualTo(0);
		assertThat(tableEntries.get(1).getWon()).isEqualTo(0); 
		
		//C
		assertThat(tableEntries.get(2).getTeamName()).isEqualTo("C");
		assertThat(tableEntries.get(2).getGoalsFor()).isEqualTo(10);
		assertThat(tableEntries.get(2).getGoalsAgainst()).isEqualTo(10);
		assertThat(tableEntries.get(2).getDrawn()).isEqualTo(1);
		assertThat(tableEntries.get(2).getGoalDifference()).isEqualTo(0);
		assertThat(tableEntries.get(2).getGoalsAgainst()).isEqualTo(10);
		assertThat(tableEntries.get(2).getLost()).isEqualTo(0);
		assertThat(tableEntries.get(2).getPlayed()).isEqualTo(1);
		assertThat(tableEntries.get(2).getPoints()).isEqualTo(1);
		assertThat(tableEntries.get(2).getWon()).isEqualTo(0); 
		
		//D
		assertThat(tableEntries.get(3).getTeamName()).isEqualTo("D");
		assertThat(tableEntries.get(3).getGoalsFor()).isEqualTo(10);
		assertThat(tableEntries.get(3).getGoalsAgainst()).isEqualTo(10);
		assertThat(tableEntries.get(3).getDrawn()).isEqualTo(1);
		assertThat(tableEntries.get(3).getGoalDifference()).isEqualTo(0);
		assertThat(tableEntries.get(3).getGoalsAgainst()).isEqualTo(10);
		assertThat(tableEntries.get(3).getLost()).isEqualTo(0);
		assertThat(tableEntries.get(3).getPlayed()).isEqualTo(1);
		assertThat(tableEntries.get(3).getPoints()).isEqualTo(1);
		assertThat(tableEntries.get(3).getWon()).isEqualTo(0); 
	}
	
	private List<Match> getSameTeamMatches() {
		String homeTeam1 = "A"; String awayTeam1 = "B"; int homeScore1 = 10; int awayScore1=5;
		String homeTeam2 = "A"; String awayTeam2 = "B"; int homeScore2 = 10; int awayScore2=5;
		String homeTeam3 = "A"; String awayTeam3 = "B"; int homeScore3 = 10; int awayScore3=5;
		Match m1 = new Match(homeTeam1,awayTeam1,homeScore1,awayScore1); 
		Match m2 = new Match(homeTeam2,awayTeam2,homeScore2,awayScore2); 
		Match m3 = new Match(homeTeam3,awayTeam3,homeScore3,awayScore3); 
		List<Match> unSortedMatches = Arrays.asList(m1,m2,m3);
		return unSortedMatches;
	}
	private List<Match> getSameTeamMatchesWithRandomTeamOrder() {
		String homeTeam1 = "A"; String awayTeam1 = "B"; int homeScore1 = 10; int awayScore1=5;
		String homeTeam2 = "B"; String awayTeam2 = "A"; int homeScore2 = 5; int awayScore2=10;
		String homeTeam3 = "A"; String awayTeam3 = "B"; int homeScore3 = 10; int awayScore3=5;
		Match m1 = new Match(homeTeam1,awayTeam1,homeScore1,awayScore1); 
		Match m2 = new Match(homeTeam2,awayTeam2,homeScore2,awayScore2); 
		Match m3 = new Match(homeTeam3,awayTeam3,homeScore3,awayScore3); 
		List<Match> unSortedMatches = Arrays.asList(m1,m2,m3);
		return unSortedMatches;
	}
	private List<Match> getSingleMatch() {
		String homeTeam1 = "A"; String awayTeam1 = "B"; int homeScore1 = 10; int awayScore1=5;
		Match m1 = new Match(homeTeam1,awayTeam1,homeScore1,awayScore1); 
		List<Match> unSortedMatches = Arrays.asList(m1);
		return unSortedMatches;
	}
	private List<Match> getTwoDifferentMatches() {
		String homeTeam1 = "A"; String awayTeam1 = "B"; int homeScore1 = 10; int awayScore1=5;
		String homeTeam2 = "C"; String awayTeam2 = "D"; int homeScore2 = 10; int awayScore2=10;
		Match m1 = new Match(homeTeam1,awayTeam1,homeScore1,awayScore1); 
		Match m2 = new Match(homeTeam2,awayTeam2,homeScore2,awayScore2); 
		List<Match> unSortedMatches = Arrays.asList(m1,m2);
		return unSortedMatches;
	}
}
