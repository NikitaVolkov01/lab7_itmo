package adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import ticket.Coordinates;

import javax.xml.bind.ValidationException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CoordinatesDeserializer implements JsonDeserializer<Coordinates> {
    @Override
    public Coordinates deserialize(
            JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {

        String data = json.getAsString();
        List<String> parts = Arrays.asList(data.split(":"));

        if(parts.contains("x") && parts.contains("y")){
            try {
                return new Coordinates(Long.parseLong(parts.get(parts.indexOf("y")+1)), Float.parseFloat(parts.get(parts.indexOf("x") + 1)));
            } catch (ValidationException e) {
                e.printStackTrace();
                return null;
            }
        }else return null;
    }
}
