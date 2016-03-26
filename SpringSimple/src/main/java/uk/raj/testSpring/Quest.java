package uk.raj.testSpring;

public class Quest {
	private int questID;
	private String questName;
	/**
	 * @param questID
	 * @param questName
	 */
	public Quest(int questID, String questName) {
		super();
		this.questID = questID;
		this.questName = questName;
	}
	public int getQuestID() {
		return questID;
	}
	public void setQuestID(int questID) {
		this.questID = questID;
	}
	public String getQuestName() {
		return questName;
	}
	public void setQuestName(String questName) {
		this.questName = questName;
	}
	
	public void embark() {
		System.out.println("Quest embarks");
	}
}
