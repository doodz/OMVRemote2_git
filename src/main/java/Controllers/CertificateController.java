package Controllers;

import android.app.Activity;
import android.net.http.SslCertificate;

import Client.AsyncCall;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;
import Models.CertificateSsh;
import Models.CertificateSsl;
import Models.SslCertificateInfoCreate;

/**
 * Created by thiba on 23/11/2016.
 */

public class CertificateController extends Abstractcontroller  {

    public CertificateController(Activity activity) {
        super(activity);
    }

    public void GetCertificate(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("CertificateMgmt");
            params.setMethod("getList");
            params.addParam("limit",25);
            params.addParam("sortdir","mNull");
            params.addParam("sortfield","mNull");
            params.addParam("start",0);

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void CreateSsl(SslCertificateInfoCreate certif, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("CertificateMgmt");
            params.setMethod("create");
            params.addParam("c",certif.getC());
            params.addParam("cn",certif.getCn());
            params.addParam("days",certif.getDays());
            params.addParam("email",certif.getEmail());
            params.addParam("l",certif.getL());
            params.addParam("o",certif.getO());
            params.addParam("ou",certif.getOu());
            params.addParam("size",certif.getSize());
            params.addParam("st",certif.getSt());

            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void Get(String uuid,Callback callBack)
    {
        //return string
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("CertificateMgmt");
            params.setMethod("get");
            params.addParam("uuid",uuid);

            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void Set(CertificateSsl cert, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("CertificateMgmt");
            params.setMethod("set");

            params.addParam("certificate",cert.getCertificate());
            params.addParam("comment",cert.getComment());
            params.addParam("privatekey",cert.getPrivatekey());
            params.addParam("uuid",cert.getUuid());

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void GetDetail(String uuid,Callback callBack)
    {
        //return string
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("CertificateMgmt");
            params.setMethod("getDetail");
            params.addParam("uuid",uuid);
            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void DeleteSsl(String uuid,Callback callBack)
    {
        //return string
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("CertificateMgmt");
            params.setMethod("delete");
            params.addParam("uuid",uuid);
            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void GetSshList(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("CertificateMgmt");
            params.setMethod("getSshList");
            params.addParam("limit",25);
            params.addParam("sortdir","mNull");
            params.addParam("sortfield","mNull");
            params.addParam("start",0);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void CreateSsh(String comment,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("CertificateMgmt");
            params.setMethod("createSsh");
            params.addParam("comment",comment);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void SetSsh(CertificateSsh cert, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("CertificateMgmt");
            params.setMethod("setSsh");
            params.addParam("publickey",cert.getPublickey());
            params.addParam("comment",cert.getComment());
            params.addParam("privatekey",cert.getPrivatekey());
            params.addParam("uuid",cert.getUuid());
            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void deleteSsh(String uuid,Callback callBack)
    {
        //return string
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("CertificateMgmt");
            params.setMethod("deleteSsh");
            params.addParam("uuid",uuid);
            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }
}
