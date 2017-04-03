package Deserializers;

import android.net.ParseException;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import Models.Value;

/**
 * Created by thiba on 31/08/2016.
 */
public class ValueDeserializer implements JsonDeserializer<Value>
{
    @Override
    public Value deserialize(JsonElement valueStr, Type typeOfSrc, JsonDeserializationContext context)
    {
        try
        {
            Value v = new Value();

            if( !valueStr.isJsonObject())
            {
                v.setText(valueStr.toString());
            }
           else
            {
                JsonElement j = valueStr.getAsJsonObject().get("text");
                v.setText(j.toString());
                j =valueStr.getAsJsonObject().get("value");
                v.setValue(j.getAsInt());
            }
            return v;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }



}

