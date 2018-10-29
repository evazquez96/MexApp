package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Date;

/**
 * Created by paqti on 06/03/2017.
 */

public class GsonHelper {
    public Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        //System.out.println(":::::::::::::::::::::::::::::::::Registering");
        builder.registerTypeAdapter(java.util.Date.class, new DotNetDateDeserializer());
        builder.registerTypeAdapter(java.util.Date.class, new DotNetDateSerializer());
        return builder.create();
    }

    public class DotNetDateDeserializer implements JsonDeserializer<java.util.Date> {
        @Override
        public Date deserialize(JsonElement json, Type typfOfT, JsonDeserializationContext context) {
            try {
                String dateStr = json.getAsString();
                /*
                if (dateStr == null)
                    return null;
                    */
                //System.out.println(":::::::::::::::::::Dotnet:" + dateStr);
                if (dateStr != null) dateStr = dateStr.replace("/Date(", "");
                if (dateStr != null) dateStr = dateStr.replace("+0000)/", "");
                if (dateStr != null) dateStr = dateStr.replace("-0600)/", "");
                if (dateStr != null) dateStr = dateStr.replace("-0500)/", "");
                if (dateStr != null) dateStr = dateStr.replace(")/", "");
                //System.out.println("******************************************************************* try" + dateStr);
                long time = Long.parseLong(dateStr);
                return new Date(time);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }

        }
    }

    public class DotNetDateSerializer implements JsonSerializer<java.util.Date> {
        @Override
        public JsonElement serialize(java.util.Date date, Type typfOfT, JsonSerializationContext context) {
            /*
            if (date == null)
                return null;
*/
            String dateStr = "/Date(" + date.getTime() + ")/";
/*
            dateStr=dateStr.replace("/Date(", "").replace("-0600)/", "");
            dateStr=dateStr.replace("-0500)/", "");
            */
            //-0500

            return new JsonPrimitive(dateStr);
        }
    }
}