package raj.streams.piping.assignment;

import java.util.Map;

public class Match {
	
	private String homeTeam;
	private String awayTeam;
	private int homeScore;
	private int awayScore;
	private static int GIVEN_MAX_TEAMNAME_LENGTH = 100;
	private static int GIVEN_MAX_SCORE = Integer.MAX_VALUE/4;
	private final static String MAX_TEAMNAME_LENGTH = "MAX_TEAMNAME_LENGTH";
	private final static String MAX_SCORE = "MAX_SCORE";
	
	static {
		try {
			GIVEN_MAX_TEAMNAME_LENGTH = Integer.parseInt(System.getProperty(MAX_TEAMNAME_LENGTH));
		}catch(Exception e) {
			GIVEN_MAX_TEAMNAME_LENGTH = 100;
			System.out.println(e.getMessage());
			System.out.println("The GIVEN_MAX_TEAMNAME_LENGTH is default set to "+100);
		}
		try {
			GIVEN_MAX_SCORE = Integer.parseInt(System.getProperty(MAX_SCORE));
		}catch(Exception e) {
			GIVEN_MAX_SCORE = Integer.MAX_VALUE/4;
			System.out.println(e.getMessage());
			System.out.println("The GIVEN_MAX_SCORE is default set to "+Integer.MAX_VALUE/4);
		}
	}
	/**
	 * 
	 * @throws InvalidInputException if the input is invalid
	 * 
	 * @param homeTeam
	 * @param awayTeam
	 * @param homeScore
	 * @param awayScore
	 */
	public Match(final String homeTeam, final String awayTeam, final int homeScore, final int awayScore) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		validate();
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public int getAwayScore() {
		return awayScore;
	}

	/**
	 * Given two entries that played in this match. Updates the following based
	 * on the business logic of the league - played, won, drawn, lost, goalsFor,
	 * goalsAgainst, goalDifference and points.
	 * 
	 * @param entries
	 */
	public void updateLeagueEntries(Map<String, LeagueTableEntry> entries) {
		LeagueTableEntry homeTeamEntry = entries.get(homeTeam);
		LeagueTableEntry awayTeamEntry = entries.get(awayTeam);
		if ( (homeTeamEntry==null) || (awayTeamEntry==null) ){
			return;
		}
		if (homeScore > awayScore) {
			homeTeamEntry.addWonCount();
			awayTeamEntry.addLostCount();
		}else if (homeScore < awayScore) {
			awayTeamEntry.addWonCount();
			homeTeamEntry.addLostCount();
		}else  { 
			homeTeamEntry.addDrawnCount();
			awayTeamEntry.addDrawnCount();
		}
		homeTeamEntry.updateGoals(homeScore,true);
		awayTeamEntry.updateGoals(awayScore,true);
		homeTeamEntry.updateGoals(awayScore,false);
		awayTeamEntry.updateGoals(homeScore,false);		
	}

	// Input validation for nulls and big O
	private void validate() throws InvalidInputException {
		if (this.awayTeam == null || this.awayTeam.trim().length() == 0)
			throw new InvalidInputException("The team names are invalid");
		if (this.awayTeam == null || this.awayTeam.trim().length() == 0)
			throw new InvalidInputException("The team names are invalid");
		if (this.homeTeam.equals(this.awayTeam))
			throw new InvalidInputException("The teams cannot play within themselves");
		if (this.homeTeam.trim().length() > GIVEN_MAX_TEAMNAME_LENGTH)
			throw new InvalidInputException(
					"The team names are longer than the permitted length of " + GIVEN_MAX_TEAMNAME_LENGTH);
		if (this.awayTeam.trim().length() > GIVEN_MAX_TEAMNAME_LENGTH)
			throw new InvalidInputException(
					"The team names are longer than the permitted length of " + GIVEN_MAX_TEAMNAME_LENGTH);
		if (this.homeScore > GIVEN_MAX_SCORE)
			throw new InvalidInputException(
					"The team score is longer than the permitted max score of " + GIVEN_MAX_SCORE);
		if (this.awayScore > GIVEN_MAX_SCORE)
			throw new InvalidInputException(
					"The team score is longer than the permitted max score of " + GIVEN_MAX_SCORE);
	}

 }
