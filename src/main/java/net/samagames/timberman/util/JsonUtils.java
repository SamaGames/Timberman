package net.samagames.timberman.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtils
{
	private JsonUtils(){}

	public static Location getLocation(JsonElement object)
    {
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
        try
        {
            float yaw = (float)json.get("yaw").getAsDouble();
            float pitch = (float)json.get("pitch").getAsDouble();
            return new Location(world, x, y, z, yaw, pitch);
        }
        catch (UnsupportedOperationException | NullPointerException ex)
        {
            return new Location(world, x, y, z);
        }
    }
}
