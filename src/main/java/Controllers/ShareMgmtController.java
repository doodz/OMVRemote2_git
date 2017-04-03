package Controllers;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Client.AsyncCall;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;
import Models.SharedFolder;

/**
 * Created by thiba on 31/10/2016.
 */

public class ShareMgmtController  extends Abstractcontroller {

    public ShareMgmtController(Activity activity) {
        super(activity);
    }


    public void getCandidates(Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            params.setService("ShareMgmt");
            params.setMethod("getCandidates");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void setSharedFolder(SharedFolder folder,String mode, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            params.setService("ShareMgmt");
            params.setMethod("set");

            params.addParam("comment",folder.getComment());
            params.addParam("mntentref",folder.getMntentref());
            params.addParam("mode",mode);
            params.addParam("name",folder.getName());
            params.addParam("reldirpath",folder.getReldirpath());
            params.addParam("uuid",folder.getUuid()!= null? folder.getUuid(): "undefined");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void getSharedFolders(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            params.setService("ShareMgmt");
            params.setMethod("getList");

            params.addParam("limit",25);
            params.addParam("sortdir","ASC");
            params.addParam("sortfield","name");
            params.addParam("start",0);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void getSharedFolderInUseList(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            params.setService("OmvExtrasOrg");
            params.setMethod("getSharedFolderInUseList");

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

    public void getResetPerms(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            params.setService("OmvExtrasOrg");
            params.setMethod("getResetPerms");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public void setResetPerms(boolean clearacl,String mode,String dirperms,String fileperms,String uuid,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            params.setService("OmvExtrasOrg");
            params.setMethod("setResetPerms");
            params.addParam("clearacl",clearacl?"mTrue":"mFalse");
            params.addParam("mode",mode);
            params.addParam("sharedfolderref",uuid);

            //if(clearacl) {
            //    params.addParam("dirperms", dirperms);
            //    params.addParam("fileperms", fileperms);
            //}
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void enumerateSharedFolders(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            params.setService("ShareMgmt");
            params.setMethod("enumerateSharedFolders");
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

    public void DeleteSharedFolder(boolean recursive,String uuid,@Nullable Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            params.setService("ShareMgmt");
            params.setMethod("delete");

            params.addParam("recursive",recursive?"mTrue":"mFalse");
            params.addParam("uuid",uuid);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static List<String> PermissionsType = new ArrayList<String>(){{add("Admin: r/w, User: no access,Other: no access");add("Admin: r/w, User: r,Other: no access");
        add("Admin: r/w, User: r/w,Other: no access");add("Admin: r/w, User: r,Other: r");add("Admin: r/w, User: r/w,Other: r");add("Everyone: read/write");}};

    public static List<String> PermissionsVal = new ArrayList<String>(){{add("700");add("750");
        add("770");add("755");add("775");add("777");}};

    public static List<String> DirpermsVal = new ArrayList<String>(){{add("2700");add("2750");
        add("2770");add("2755");add("2775");add("2777");}};

    public static List<String> FilepermsVal = new ArrayList<String>(){{add("600");add("640");
        add("660");add("644");add("664");add("666");}};
}
