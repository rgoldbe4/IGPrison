package us.ignitiongaming.enums;

public enum IGPlayerGangRequestAnswer {

	UNANSWERED (0), ACCEPTED (1), DECLINED (2);
	
	private int ID;
	
	private IGPlayerGangRequestAnswer(int ID) { this.ID = ID; }
	
	public int getId() { return ID; }
	
	public static IGPlayerGangRequestAnswer getAnswerById(int ID) {
		for (IGPlayerGangRequestAnswer answer : IGPlayerGangRequestAnswer.values()) {
			if (answer.getId() == ID) return answer;
		}
		return IGPlayerGangRequestAnswer.UNANSWERED;
	}
}
