package Client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import Interfaces.IJSONRPCParamsBuilder;

/**
 * Created by thiba on 16/08/2016.
 */
public class JSONRPCParamsBuilder implements IJSONRPCParamsBuilder {

    private String mMethod;
    private String mService;
    private String mParamsStr;
    private HashMap<String, Integer> mIntParams;
    private HashMap<String, Long> mLongParams;

    private HashMap<String, String> mStrParams;
    private HashMap<String, Boolean> mBoolParams;
    private HashMap<String, Object> mObjectParams;
    private HashMap<String, Integer> mIntOptions;
    private HashMap<String, Boolean> mBoolOptions;
    private HashMap<String, String> mStrOptions;

    private JSONArray mParams = null;

    public void setMethod(String method) {
        mMethod = method;
    }

    public void setService(String service) {
        mService = service;
    }

    public void addParam(String name, String value) {
        mStrParams.put(name, value);
    }

    public void addParam(String name, int value) {
        mIntParams.put(name, value);
    }
    public void addParam(String name, long value) {
        mLongParams.put(name, value);
    }

    public void addObjParam(String name, Object value) {

        mObjectParams.put(name, value);
    }

    public void addParam(String name, Boolean value) {
        mBoolParams.put(name, value);
    }

    public void addOption(String name, String value) {
        mStrOptions.put(name, value);
    }

    public void addOption(String name, int value) {
        mIntOptions.put(name, value);
    }

    public void addOption(String name, Boolean value) {
        mBoolOptions.put(name, value);
    }

    public void SetparamArray(JSONArray obj){mParams = obj; }
    public void SetparamStr(String paramsStr){mParamsStr = paramsStr; }

    public JSONRPCParamsBuilder()
    {
        mIntParams = new HashMap<String, Integer>();
        mLongParams =  new HashMap<String, Long>();
        mStrParams = new HashMap<String, String>();
        mBoolParams = new HashMap<String, Boolean>();
        mObjectParams = new HashMap<String, Object>();
        mIntOptions = new HashMap<String, Integer>();
        mStrOptions = new HashMap<String, String>();
        mBoolOptions= new HashMap<String, Boolean>();
    }

    private JSONObject buildParam() {

        //Login :
        //{"service":"Session","method":"login","params":{"username":"admin","password":"openmediavault"},"options":null}
        //SystemInformation :
        //{service: "Config", method: "isDirty", params: null, options: {updatelastaccess: false}}

        JSONObject object = new JSONObject();
        JSONObject params =  new JSONObject();
        JSONObject options = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            object.put("service", mService);
            object.put("method", mMethod);

            if(mParams == null && mParamsStr == null) {
                if (mIntParams.size() > 0) {
                    for (String key : mIntParams.keySet()) {
                        params.put(key, mIntParams.get(key));
                    }
                }

                if (mLongParams.size() > 0) {
                    for (String key : mLongParams.keySet()) {
                        params.put(key, mLongParams.get(key));
                    }
                }

                if (mStrParams.size() > 0) {
                    for (String key : mStrParams.keySet()) {
                        params.put(key, mStrParams.get(key));
                    }
                }

                if (mBoolParams.size() > 0) {
                    for (String key : mBoolParams.keySet()) {
                        params.put(key, mBoolParams.get(key));
                    }
                }

                if (mObjectParams.size() > 0) {
                    for (String key : mObjectParams.keySet()) {
                        params.put(key, (ArrayList) mObjectParams.get(key));
                    }
                }
                if(params.length() > 0)
                    object.put("params", params);
                else
                    object.put("params", "mNull");
            }
            else
            {
                if(mParamsStr != null)
                    object.put("params", mParamsStr);
                else
                    object.put("params", mParams);



            }
            if (mBoolOptions.size() > 0) {
                for (String key : mBoolOptions.keySet()) {
                    options.put(key, mBoolOptions.get(key));
                }
            }

            if (mIntOptions.size() > 0) {
                for (String key : mIntOptions.keySet()) {
                    options.put(key, mIntOptions.get(key));
                }
            }

            if (mStrOptions.size() > 0) {
                for (String key : mStrOptions.keySet()) {
                    options.put(key, mStrOptions.get(key));
                }
            }

            if(options.length() > 0)
                object.put("options", options);
            else
            object.put("options", "mNull");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }


    @Override
    public String toString()
    {
        JSONObject params = buildParam();
        String entity = params.toString();
        entity =entity.replace("\"mNull\"","null");
        entity =entity.replace("\"mFalse\"","false");
        entity =entity.replace("\"mTrue\"","true");
        entity =entity.replace("\"[","[");
        entity =entity.replace("]\"","]");
        entity =entity.replace("\"{","{");
        entity =entity.replace("}\"","}");
        entity =entity.replace("\\\"","\"");
        return entity;
    }
}
