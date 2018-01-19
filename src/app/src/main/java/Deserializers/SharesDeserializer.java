package Deserializers;

import android.net.ParseException;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

import Models.Privileges;
import Models.Shares;
import utils.Util;

/**
 * Created by Ividata7 on 08/02/2017.
 */

public class SharesDeserializer  implements JsonDeserializer<Shares> {

    @Override
    public Shares deserialize(JsonElement valueStr, Type typeOfSrc, JsonDeserializationContext context)
    {
        try
        {
            Shares res = new Shares();

            if(valueStr.isJsonObject())
            {
                res = Util.FromJson(valueStr,Shares.class);
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
