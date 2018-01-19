package Deserializers;

import android.net.ParseException;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

import Models.SystemInformation;

/**
 * Created by thiba on 31/08/2016.
 */
public class SystemInformationDeserializer implements JsonDeserializer<SystemInformation>
{
    @Override
    public SystemInformation deserialize(JsonElement valueStr, Type typeOfSrc, JsonDeserializationContext context)
    {
        try
        {
            SystemInformation v = new SystemInformation();

            return v;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }



}
