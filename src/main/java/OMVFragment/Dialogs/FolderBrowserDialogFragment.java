package OMVFragment.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.dev.doods.base.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapters.FolderTreeAdapter;
import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.FolderBrowserController;
import Interfaces.NoticeDialogListener;
import Models.Errors;
import OMV.Classe.TreeFolderNode;

/**
 * Created by thiba on 06/11/2016.
 */

public class FolderBrowserDialogFragment  extends DialogFragment implements android.view.View.OnClickListener {



    ListView lstNodeView;
    FolderTreeAdapter adapter;

    private TreeFolderNode selectedNode;
    private String mntent = "mntent";
    private String CurrentPath = "";
    private String Uuid;

    private List<TreeFolderNode> lstNode = new ArrayList<TreeFolderNode>();

    TreeFolderNode root = new TreeFolderNode(null,"/");

    private FolderBrowserController mFolderBrowserController = new FolderBrowserController(getActivity());
    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;
    private Handler handler = new Handler();

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Bundle bundle = getArguments();

        Uuid = bundle.getString("UUID");

        final View mView = inflater.inflate(R.layout.dialog_folder_browser, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton("Get", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //EditText et = (EditText)mView.findViewById(R.id.filterOnName);


                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(FolderBrowserDialogFragment.this);
                    }
                })
                .setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(FolderBrowserDialogFragment.this);
                    }
                });


        mFolderBrowserController.getListFilesFolder(CurrentPath, mntent, Uuid, new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                final ArrayList<String> res = response.GetResultObject(new TypeToken<ArrayList<String>>(){});
                handler.post(new Runnable(){
                    public void run() {
                        UpdateFolderLst(res);
                    }
                });

            }
        });

        lstNode.add(root);

        lstNodeView = (ListView) mView.findViewById(R.id.lstNode);
        adapter = new FolderTreeAdapter(getActivity(), lstNode);

        adapter.on_click_listener = this;
        lstNodeView.setAdapter(adapter);

        selectedNode = root;
        return builder.create();
    }

    private void GetFolder()
    {
        mFolderBrowserController.getListFilesFolder(selectedNode.Path(), mntent, Uuid, new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                final ArrayList<String> res = response.GetResultObject(new TypeToken<ArrayList<String>>(){});
                handler.post(new Runnable(){
                    public void run() {
                        UpdateFolderLst(res);
                    }
                });

            }
        });

    }

    private void UpdateFolderLst(List<String> res)
    {
        selectedNode.gotChildren = true;
        selectedNode.is_open = true;
        for (String str :res) {

            TreeFolderNode node = new TreeFolderNode(selectedNode,str);
            lstNode.add(node);
        }
        this.adapter.clear();
        this.adapter.addAll(TreeFolderNode.Get_Visible_Nodes(null, this.lstNode));
    }

    @Override
    public void onClick(View view) {
        TreeFolderNode n;

        n=(TreeFolderNode)view.getTag();
        selectedNode = n;

        for (TreeFolderNode node: lstNode)
        {
            node.is_open = false;
        }

        selectedNode.setChechedNodes();

        if(!selectedNode.gotChildren)
            GetFolder();

        if (view instanceof android.widget.CheckBox)
            {
              n.is_open = ((android.widget.CheckBox)view).isChecked();
              this.adapter.clear();
              this.adapter.addAll(TreeFolderNode.Get_Visible_Nodes(null, this.lstNode));
            }
        else
          android.widget.Toast.makeText(getActivity(), n.data.toString(), android.widget.Toast.LENGTH_LONG).show();
    }

    public TreeFolderNode getSelectedNode()
    {
        return selectedNode;
    }

    //TreeNode root = TreeNode.root();
    //TreeNode parent = new TreeNode("node-name");
    //TreeNode child0 = new TreeNode("child-node-name-1");
    //TreeNode child1 = new TreeNode("child_node-name-2");

    //parent.addChildren(child0, child1);
    //root.addChild(parent);

}
