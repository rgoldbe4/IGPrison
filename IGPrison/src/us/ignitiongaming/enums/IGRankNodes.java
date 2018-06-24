package us.ignitiongaming.enums;

import org.bukkit.entity.Player;

public enum IGRankNodes {
	//node, tag, nameColor, isStaff (default: false)
	SOLITARY ("igprison.solitary", "§7[§8lD§r§7] §r", "§8"),
	D ("igprison.d", "§8[§5D§8] §r", "§5"),
	C ("igprison.c", "§8[§2C§8] §r", "§2"),
	B ("igprison.b", "§8[§eB§8] §r", "§e"),
	A ("igprison.a", "§8[§cA§8] §r", "§c"),
	FREE ("igprison.free", "§8[§aFree§8] §r", "§a"),
	GUARD ("igprison.guard", "§8[§6Guard§8] §r", "§6", true),
	WARDEN ("igprison.warden", "§8[§4Warden§8] §r", "§4", true),
	STAFF ("igprison.staff", "§8[§bStaff§8] §r", "§b", true);
	
	private String node, tag, nameColor;
	private boolean isStaff;
	
	private IGRankNodes(String node, String tag, String nameColor) {
		this(node, tag, nameColor, false);
	}
	
	private IGRankNodes(String node, String tag, String nameColor, boolean isStaff) { 
		this.node = node;
		this.tag = tag;
		this.nameColor = nameColor;
		this.isStaff = isStaff;
	}
	
	public String getNode() { return node; }
	public boolean isStaff() { return isStaff; }
	public String getTag() { return tag; }
	public String getNameColor() { return nameColor; }
	
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
		//Player doesn't have any permissions? Give them solitary...
		return IGRankNodes.D;
	}
	
	
}
