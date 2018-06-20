package us.ignitiongaming.enums;

public enum SignOres {

IRON ("IRON"), GOLD ("GOLD"), DIAMOND ("DIAMOND"), EMERALD ("EMERALD");
	
	private String name;
	
	private SignOres(String name) {
		this.name = name;
	}
	
	public String getName() { return name.toUpperCase(); }
	public static SignOres getItem(String name) {
		SignOres expected = SignOres.IRON;
		for (SignOres item : SignOres.values()) {
			if (item.getName().equalsIgnoreCase(name)) expected = item;
		}
		return expected;
	}
}
