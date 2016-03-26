package uk.raj.testSpring;

public class BraveKnight implements Knight {
	private Quest quest;
		
	/**
	 * @param quest
	 */
	public BraveKnight(Quest quest) {
		super();
		this.quest = quest;
	}

	@Override
	public void embarkOnQuest(Quest quest) {
		// TODO Auto-generated method stub
		quest.embark();
	}

}
