package adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ticket.Coordinates;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class CoordinatesSerializer implements JsonSerializer<Coordinates> {
    @Override
    public JsonElement serialize(Coordinates coords, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("x", coords.getX());
        result.addProperty("y", coords.getY());
        return result;
    }
}