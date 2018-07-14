package us.ignitiongaming.enums;

public enum IGPlayerGangRequestAnswer {

	UNANSWERED (0, "§ePending"), ACCEPTED (1, "§aAccepted"), DECLINED (2, "§cDeclined");
	
	private int ID;
	private String label;
	
	private IGPlayerGangRequestAnswer(int ID, String label) { this.ID = ID; this.label = label; }
	
	public int getId() { return ID; }
	public String getLabel() { return label; }
	
	public static IGPlayerGangRequestAnswer getAnswerById(int ID) {
		for (IGPlayerGangRequestAnswer answer : IGPlayerGangRequestAnswer.values()) {
			if (answer.getId() == ID) return answer;
		}
		return IGPlayerGangRequestAnswer.UNANSWERED;
	}
}
