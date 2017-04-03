package com.dev.doods.omvremote2.Plugins.LetsEncrypt;

import android.app.Activity;

import Client.Callback;
import Client.JSONRPCParamsBuilder;
import Controllers.Abstractcontroller;

/**
 * Created by Ividata7 on 31/03/2017.
 */

public class LetsEncryptController  extends Abstractcontroller {

    public LetsEncryptController(Activity activity) {
        super(activity);
    }

    public void GetSettings( Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("LetsEncrypt");
            params.setMethod("getSettings");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void SetSettings(LetsEncrypt conf, Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("LetsEncrypt");
            params.setMethod("setSettings");

            params.addParam("enable",conf.getEnable()?"mTrue":"mFalse");
            params.addParam("test_cert",conf.getTestCert()?"mTrue":"mFalse");
            params.addParam("domain",conf.getDomain());
            params.addParam("email",conf.getEmail());
            params.addParam("webroot",conf.getWebroot());
            params.addParam("cron_uuid",conf.getCronUuid());
            params.addParam("certuuid",conf.getCertuuid());
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }
    public void GenerateCertificate( Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("LetsEncrypt");
            params.setMethod("generateCertificate");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

}
