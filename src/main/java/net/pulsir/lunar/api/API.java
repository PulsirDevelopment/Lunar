package net.pulsir.lunar.api;

import net.pulsir.lunar.api.bungee.BungeeAPI;
import net.pulsir.lunar.api.inventory.InventoryAPI;
import net.pulsir.lunar.api.redis.RedisAPI;
import net.pulsir.lunar.api.staff.StaffAPI;

public class API {

    public StaffAPI getStaff(){
        return new StaffAPI();
    }

    public RedisAPI getRedis() {
        return new RedisAPI();
    }

    public BungeeAPI getBungee(){
        return new BungeeAPI();
    }

    public InventoryAPI getInventory(){
        return new InventoryAPI();
    }
}
