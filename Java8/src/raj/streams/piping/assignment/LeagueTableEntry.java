package raj.streams.piping.assignment;

/**
 * 
 * Key values for the entry are as follows :
 *  played = Games Played 
 *	won = Games Won 
 *	drawn = Games Drawn 
 *	lost = Games Lost 
 *	goalsFor = Goal For (scored) 
 *	goalsAgainst = Goals Against (conceded) 
 *	goalDifference = Goal Difference ( Goals scored less goals conceded) 
 *	points = Points (3 for a win, 1 for a draw). 
 * 
 * @author Rajesh
 *
 */
public class LeagueTableEntry {
	private static final int WIN_POINT_SCORE = 3;
	private static final int DRAW_POINT_SCORE = 1;
	private String teamName;
	private int played;
	private int won;
	private int drawn;
	private int lost;
	private int goalsFor;
	private int goalsAgainst;
	private int goalDifference;
	private int points;

	public LeagueTableEntry(String teamName) {
		this.teamName = teamName;
	}

	public LeagueTableEntry(String teamName, int played, int won, int drawn, int lost, int goalsFor, int goalsAgainst,
			int goalDifference, int points) {
		this.teamName = teamName;
		this.played = played;
		this.won = won;
		this.drawn = drawn;
		this.lost = lost;
		this.goalsFor = goalsFor;
		this.goalsAgainst = goalsAgainst;
		this.goalDifference = goalDifference;
		this.points = points;
	}

	public String getTeamName() {
		return teamName;
	}

	public int getPlayed() {
		return played;
	}

	public int getWon() {
		return won;
	}

	public int getDrawn() {
		return drawn;
	}

	public int getLost() {
		return lost;
	}

	public int getGoalsFor() {
		return goalsFor;
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	public int getGoalDifference() {
		return goalDifference;
	}

	public int getPoints() {
		return points;
	}

	private void addPlayedCount() {
		this.played++;
	}

	public void addWonCount() {
		this.won++;
		this.points = this.points + WIN_POINT_SCORE;
		addPlayedCount();
	}

	public void addLostCount() {
		this.lost++;
		addPlayedCount();
	}

	public void addDrawnCount() {
		this.drawn++;
		this.points = this.points + DRAW_POINT_SCORE;
		addPlayedCount();
	}

	public void updateGoals(int goals, boolean goalFor) {
		if (goalFor) 
			goalsFor += goals;
		else 
			goalsAgainst += goals;
		goalDifference = goalsFor - goalsAgainst;
	}
}
