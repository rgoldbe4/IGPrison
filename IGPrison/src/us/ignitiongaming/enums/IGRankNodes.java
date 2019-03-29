package us.ignitiongaming.enums;

import org.bukkit.entity.Player;

public enum IGRankNodes {
	//node, tag, nameColor, isStaff (default: false)
	SOLITARY ("igprison.solitary", "§7[§8§lSolitary§r§7] §r", "§8", false, false),
	D ("igprison.d", "§8[§5D§8] §r", "§5", false, true),
	C ("igprison.c", "§8[§2C§8] §r", "§2", false, true),
	B ("igprison.b", "§8[§eB§8] §r", "§e", false, true),
	A ("igprison.a", "§8[§cA§8] §r", "§c", false, true),
	FREE ("igprison.free", "§8[§aFree§8] §r", "§a", false, false),
	GUARD ("igprison.guard", "§8[§6Guard§8] §r", "§6", true, false),
	WARDEN ("igprison.warden", "§8[§4Warden§8] §r", "§4", true, false),
	ARCHITECT ("igprison.architect", "§8[§bArchitect§8] §r", "§b", true, false),
	OWNER ("igprison.owner", "§8[§bOwner§8] §r", "§b", true, false),
	STAFF ("igprison.staff", "§8[§bStaff§8] §r", "§b", true, false);
	
	private String node, tag, nameColor;
	private boolean isStaff, isInJail;
	
	private IGRankNodes(String node, String tag, String nameColor, boolean isStaff, boolean isInJail) { 
		this.node = node;
		this.tag = tag;
		this.nameColor = nameColor;
		this.isStaff = isStaff;
		this.isInJail = isInJail;
	}
	
	public String getNode() { return node; }
	public boolean isStaff() { return isStaff; }
	public String getTag() { return tag; }
	public String getNameColor() { return nameColor; }
	public boolean isInJail() { return isInJail; }
	
	/**
	 * Get a player's rank based on permission nodes.
	 * <i>If op, then given STAFF. If no rank found, they're given cell D.</i>
	 * @param player
	 * @return
	 */
	public static IGRankNodes getPlayerRank(Player player) {
		//Operator basically has all permissions, so let's just give em staff.
		if (player.isOp()) return IGRankNodes.STAFF;
		for (IGRankNodes rank : IGRankNodes.values()) {
			if (player.hasPermission(rank.getNode())) return rank;
		}
		//Player doesn't have any permissions? Give them the default...
		return IGRankNodes.D;
	}
	
	public static boolean isPlayerInSolitary(Player player) {
		return (player.hasPermission(IGRankNodes.SOLITARY.getNode()));
	}
	
	public static String getPlayerFormatting(Player player) {
		IGRankNodes rank = getPlayerRank(player);
		return rank.getFormatting() + player.getName();
	}
	
	public String getFormatting() {
		return tag + nameColor;
	}
	
	
}
