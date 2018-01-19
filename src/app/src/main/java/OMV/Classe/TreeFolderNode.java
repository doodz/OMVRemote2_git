package OMV.Classe;

import java.util.List;

/**
 * Created by thiba on 07/11/2016.
 */

public class TreeFolderNode {

    public boolean is_open;
    public TreeFolderNode parent;
    public Object data;
    public boolean gotChildren;

    public TreeFolderNode(TreeFolderNode parent, Object data)
       {
         this.is_open = false;
        this.parent = parent;
        this.data = data;
      }

    public boolean Has_Children(List<TreeFolderNode> nodes)
      {
        boolean res=false;

        for (TreeFolderNode n: nodes)
          if (n.parent == this)
          {
            res = true;
            break;
          }

        return res;
      }

      public int Count_Parents()
      {
        int res=0;
          TreeFolderNode n;

        for (n=this; n.parent!=null; n=n.parent)
          res++;

        return res;
      }

      public String Path()
      {
          return (parent == null)?"": parent.Path() +"/"+data.toString();
      }

      public static java.util.ArrayList<TreeFolderNode> Get_Visible_Nodes(TreeFolderNode parent, List<TreeFolderNode> nodes)
      {
          java.util.ArrayList<TreeFolderNode> visible_nodes=null;

            visible_nodes=new java.util.ArrayList<TreeFolderNode>();
            for (TreeFolderNode n: nodes)
                if (n.parent==parent)
                {
                    visible_nodes.add(n);
                    if (n.is_open)
                      visible_nodes.addAll(Get_Visible_Nodes(n, nodes));
                }

          return visible_nodes;
      }


    public void setChechedNodes()
    {
        this.is_open = true;
        if(parent !=null)
            parent.setChechedNodes();
    }
}
