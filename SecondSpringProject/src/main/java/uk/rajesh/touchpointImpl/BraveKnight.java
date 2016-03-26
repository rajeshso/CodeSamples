package uk.rajesh.touchpointImpl;

import org.springframework.beans.factory.annotation.Autowired;

import uk.rajesh.touchpoint.Knight;
import uk.rajesh.touchpoint.Quest;

public class BraveKnight implements Knight {
	
	@Autowired
	private Quest quest;
	
	private String knightID;

	public BraveKnight() {
		super();
	}

	/**
	 * @param quest
	 */
	public BraveKnight(Quest quest) {
		super();
		this.quest = quest;
	}

	//@Override
	public void embarkOnQuest() {
		System.out.println("Embarked on Quest..Invoking the given quest "+ this.knightID);
		quest.embark();
	}
	
	public Quest getQuest() {
		return quest;
	}

	public String getKnightID() {
		return knightID;
	}

	public void setKnightID(String knightID) {
		this.knightID = knightID;
	}	
}
