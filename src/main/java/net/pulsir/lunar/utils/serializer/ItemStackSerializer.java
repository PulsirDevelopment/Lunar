package net.pulsir.lunar.utils.serializer;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ItemStackSerializer {

    public static String serialize(ItemStack itemStack){
        String serialize;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
            bukkitObjectOutputStream.writeObject(itemStack);
            bukkitObjectOutputStream.flush();
            byte[] serializedObject = byteArrayOutputStream.toByteArray();
            serialize = new String(Base64.getEncoder().encode(serializedObject));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return serialize;
    }

    public static ItemStack deSerialize(String stringItemStack){
        byte[] deSerializedObject = Base64.getDecoder().decode(stringItemStack);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(deSerializedObject);
        try {
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);
            return (ItemStack) bukkitObjectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}