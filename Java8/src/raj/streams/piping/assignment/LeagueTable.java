package raj.streams.piping.assignment;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * League Table receives the list of played Matches as input
 * Single Responsibility -  Maintain the League Table
 * Functions of League Table 
 *  * Build League Table Entries
 *  * Provide a sorted List of League Table Entries based on  points, goal difference, goals for and then team names. 
 * The Rules for Scoring 3 points for a win, 1 point for a draw.  
 * 
 * Rules for the League table can be understood in this weblink
 * https://uk.answers.yahoo.com/question/index?qid=20100921145249AAWNhQf
 * 
 * A sample League table weblink
 * http://www.bbc.co.uk/sport/football/tables
 * 
 * @author Rajesh
 *
 */
public class LeagueTable {
	
	private List<Match> matches;
	private Map<String,LeagueTableEntry> leagueTableEntries;
	
	/**
	 * Create a league table from the supplied list of match results
	 * 
	 * @param matches
	 */
	public LeagueTable(final List<Match> matches) {
		this.matches = matches;
		buildLeagueTableEntriesMap();
		updateMatchOutcomes();
	}

	/**
	 * Get the ordered list of league table entries for this league table.
	 * 	The sort order is by points, goal difference, goals for and then team names. 
	 * 
	 * @return
	 */
	public List<LeagueTableEntry> getTableEntries() {
		return leagueTableEntries.values().stream().sorted((p1,p2)-> Integer.compare(p2.getPoints(), p1.getPoints())).sorted((gd1, gd2)-> Integer.compare(gd2.getGoalDifference(), gd1.getGoalDifference())).sorted(comparing(LeagueTableEntry::getTeamName)).collect(toList());
	}
	
	/**
	 * Builds the initial leagueTableEntries map with all the available team names.
	 * No other field other than the team names would be populated in the League Table
	 */
	private void buildLeagueTableEntriesMap() {
		leagueTableEntries = Stream.concat(matches.stream().map(Match::getHomeTeam), matches.stream().map(Match::getAwayTeam)).distinct().collect(toMap(String::toString, teamName->new LeagueTableEntry(teamName) ));
	}

	/**
	 * Updates the leagueTableEntries map with the outcome of every available match.
	 * All the fields such as the wins, losses, draws, points et al would be populated.
	 */
	private void updateMatchOutcomes() {
		matches.stream().forEach(match->match.updateLeagueEntries(getLeagueTableEntryPair(match)));
	}

	/**
	 * Returns the LeagueTableEntry for the two team that took part in a match
	 * 
	 * @param match Match
	 * @return
	 */
	private Map<String,LeagueTableEntry> getLeagueTableEntryPair(Match match) {
		return leagueTableEntries.keySet().stream().filter(teamName -> teamName==match.getHomeTeam() || teamName==match.getAwayTeam()).collect(toMap(String::toString, teamName->leagueTableEntries.get(teamName)));
	}
	/**
	 * Returns the list of matches that are used in this League Table
	 * @return the list of matches that are used in this League Table
	 */
	public List<Match> getMatches() {
		return this.matches;
	}
}
