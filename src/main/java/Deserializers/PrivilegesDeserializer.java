package Deserializers;

import android.net.ParseException;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

import Models.Privileges;
import Models.Value;
import utils.Util;

/**
 * Created by thiba on 01/12/2016.
 */

public class PrivilegesDeserializer  implements JsonDeserializer<Privileges> {

    @Override
    public Privileges deserialize(JsonElement valueStr, Type typeOfSrc, JsonDeserializationContext context)
    {
        try
        {
            Privileges res = new Privileges();

            if(valueStr.isJsonObject())
            {
                res = Util.FromJson(valueStr,Privileges.class);
            }

            return res;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
