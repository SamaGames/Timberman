package net.samagames.timberman.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtils
{
    private JsonUtils(){
    }

    public static Location getLocation(JsonElement object)
    {
        if (object == null)
            return null;
        JsonObject json = object.getAsJsonObject();
        String w = json.get("world").getAsString();
        if (w == null)
            return null;
        World world = Bukkit.getWorld(w);
        if (world == null)
            return null;
        double x = json.get("x").getAsDouble();
        double y = json.get("y").getAsDouble();
        double z = json.get("z").getAsDouble();
        Location loc = new Location(world, x, y, z);
        JsonElement elem = json.get("yaw");
        if (elem != null)
            loc.setYaw(elem.getAsFloat());
        elem = json.get("pitch");
        if (elem != null)
            loc.setPitch(elem.getAsFloat());
        return loc;
    }
}
