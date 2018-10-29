package utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Date;

/**
 * Created by paqti on 06/03/2017.
 */

public class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement element, Type arg1,
                            JsonDeserializationContext arg2) throws JsonParseException {
        String data = element.getAsString();
        data = data.replace("/Date(", "");
        data = data.replace(")/", "");
        try {
            return new Date(Long.parseLong(data));
        } catch (Exception e) {
            return null;
        }
    }
}
