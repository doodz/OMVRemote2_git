package Client;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import Models.Errors;
import utils.Util;

/**
 * Created by thiba on 17/08/2016.
 */
public class Response{

    private Type typeResponseClass;
    private JsonElement mJsonResponse;
    private Errors mErrors;

    public Response()
    {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericSuperclass;
            typeResponseClass = pt.getActualTypeArguments()[0];
        }

    }

    public  JsonElement jsonResponse()
    {
        return mJsonResponse;

    }
    public Response( JsonElement jsonResponse  )
    {
        mJsonResponse = jsonResponse;

    }


    public Boolean Error()
    {
        boolean b = false;
        if(mJsonResponse == null)
        {
            Log.i("Response getError","mJsonResponse is null");
            return true;
        }
        try {
            if(!mJsonResponse.isJsonObject())
            {
                Log.i("Response getError","mJsonResponse is not an json object");
                return true;
            }
            JsonObject obj = mJsonResponse.getAsJsonObject();

            JsonElement j = obj.get("error");
             b = j.isJsonNull();
        }
       catch (IllegalStateException ex)
       {
           Log.e("Response getError","IllegalStateException =>"+mJsonResponse.toString());
           ex.printStackTrace();
           throw ex;

       }
        return !b;
    }
    public JsonElement GetJsonError()
    {
        if(mJsonResponse == null || mJsonResponse instanceof JsonNull)
        {
            Log.i("Response","Response is null");
            Errors objNUll = new Errors();
            objNUll.setMessage("Response is null");
            objNUll.setTrace("Response is null");
            objNUll.setCode(42);

            Gson gson = new Gson();
            return gson.toJsonTree(objNUll);
        }

        JsonObject obj = mJsonResponse.getAsJsonObject();
        return obj.get("error");
    }

    public Errors GetErrorObject()
    {

        if(mErrors == null) {
            JsonElement j = GetJsonError();
            TypeToken tt = new TypeToken<Errors>() {
            };
            Type t = tt.getType();
            mErrors = Util.FromJson(j, t);
        }

        return mErrors;


    }
    public Boolean Result()
    {
        JsonObject obj = mJsonResponse.getAsJsonObject();
        JsonElement j = obj.get("response");
        boolean b = obj.isJsonNull();
        return !b;
    }

    public JsonElement GetJsonResult()
    {
        JsonObject obj = mJsonResponse.getAsJsonObject();
        return obj.get("response");
    }
    @SuppressWarnings("unchecked")
    public <T> T  GetResultObject(TypeToken tt)
    {
        JsonElement j = GetJsonResult();
        Type t =  tt.getType();
        return (T)Util.FromJson(j,t);
    }



}
