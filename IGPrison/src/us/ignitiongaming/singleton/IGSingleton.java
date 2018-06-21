package us.ignitiongaming.singleton;

import java.util.ArrayList;
import java.util.UUID;

public class IGSingleton {
	private ArrayList<UUID> staffchatters;
	private static IGSingleton uniqueInstance = new IGSingleton();
	private IGSingleton(){
		staffchatters = new ArrayList<UUID>();
	}
	public static IGSingleton getInstance(){
		return uniqueInstance;
	}
	public void toggleStaffChatter(UUID playerID){
		if(staffchatters.contains(playerID))staffchatters.remove(playerID);
		else staffchatters.add(playerID);
	}
	public ArrayList<UUID> getStaffChatters() {
		return staffchatters;
	}
}
