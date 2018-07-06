package us.ignitiongaming.util.items;

import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BookGenerator {
	public static void createBook(ItemMeta itemMeta, String content, String title) {
		if(itemMeta instanceof BookMeta) {
			BookMeta bookMeta = (BookMeta) itemMeta;
			String[] pages = content.split("`");
			for(int i = 0; i < pages.length;  i++){
				bookMeta.setPage(i,pages[i]);
			}
			bookMeta.setAuthor("IgnitionGaming.us");
			bookMeta.setGeneration(BookMeta.Generation.TATTERED);
			bookMeta.setTitle(title);
		}
	}
}
