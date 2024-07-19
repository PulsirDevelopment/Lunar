package net.pulsir.lunar.utils.base64;

import net.pulsir.lunar.utils.serializer.ItemStackSerializer;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Base64 {

    public static ItemStack[] toInventory(String base64String){
        byte[] byteArray = java.util.Base64.getDecoder().decode(base64String);

        String combinedString = new String(byteArray);
        String[] elements = combinedString.split(",");

        List<String> list = new ArrayList<>(Arrays.asList(elements));

        ItemStack[] itemStacks = new ItemStack[list.size()];
        for (int i = 0; i < list.size(); i++) {
            itemStacks[i] = ItemStackSerializer.deSerialize(list.get(i));
        }

        return itemStacks;
    }

    public static String toBase64(List<String> items) {
        StringJoiner joiner = new StringJoiner(",");
        for (final String item : items) {
            joiner.add(item);
        }

        String result = joiner.toString();

        byte[] byteArray = result.getBytes();
        return java.util.Base64.getEncoder().encodeToString(byteArray);
    }
}
