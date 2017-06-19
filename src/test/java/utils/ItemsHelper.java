package utils;

import java.util.ArrayList;
import java.util.List;

import com.suri.item.model.Item;

/**
 * @author lakshay13@gmail.com
 */
public class ItemsHelper {

    public static List<Item> getItemList() {
        List<Item> itemList = new ArrayList<Item>();

        Item item1 = new Item(1, "TennisRacquet", "Sports", "Av", 2500L);
        Item item2 = new Item(2, "GymShorts", "Sports", "Av", 3000L);
        Item item3 = new Item(3, "GymShoes", "Sports", "NotAv", 3500L);

        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);

        return itemList;
    }
}
