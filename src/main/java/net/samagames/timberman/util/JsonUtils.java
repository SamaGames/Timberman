package net.samagames.timberman.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/*
 * This file is part of Timberman.
 *
 * Timberman is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Timberman is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Timberman.  If not, see <http://www.gnu.org/licenses/>.
 */
public class JsonUtils
{
    private JsonUtils()
    {
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
