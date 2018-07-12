package us.ignitiongaming.util.calculator;

import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.entity.gang.IGGang;
import us.ignitiongaming.enums.IGSettings;
import us.ignitiongaming.factory.gang.IGPlayerGangFactory;

public class GangCalculator {

	public static double costforNewMember(IGGang igGang) {
		//Step 1: Get the number of members in the gang.
		int memberCount = IGPlayerGangFactory.getPlayersInGang(igGang.getId()).size();
		
		//Step 2: Get the amount per member (based on server settings).
		double memberCost = Double.parseDouble(ServerDefaults.getSetting(IGSettings.ADD_GANG_MEMBER_PRICE).getValue().toString());
		
		//Step 3: Calculate...
		double newMemberCost = memberCost * memberCount;
		
		return newMemberCost;
	}
}
