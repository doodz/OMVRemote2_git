package OMVFragment;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;

import Controllers.ShareMgmtController;
import Interfaces.IFragmentButton;
import Interfaces.IFragmentInteraction;
import Interfaces.OnFragmentInteractionListener;

/**
 * Created by thiba on 31/10/2016.
 */

public class FragmentInteractionBase extends Fragment implements IFragmentInteraction {

    private OnFragmentInteractionListener mListener;
    public Boolean HaveUpdateMethode = false;

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void OnMessage(String msg)
    {
        if (mListener != null) {
            mListener.onFragmentMessage(msg);
        }
    }
    public void OnError(String msg)
    {
        if (mListener != null) {
            mListener.onFragmentMessageError(msg);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void Update() {


    }

    public boolean HaveUpdateMethode()
    {

       return HaveUpdateMethode;

    }

    @Override
    public int GetIconActionId() {
        return 0;
    }
}
