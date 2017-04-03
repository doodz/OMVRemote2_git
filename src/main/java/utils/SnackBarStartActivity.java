package utils;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.dev.doods.omvremote2.MyApplicationBase;
import com.dev.doods.base.R;

//import org.acra.ACRA;

/**
 * Created by Ividata7 on 27/01/2017.
 */

public class SnackBarStartActivity {

    public SnackBarStartActivity(View v, String message, Class<?> cls)
    {

        this.set(v,message, cls);
    }

    private void set(View v, String message, final Class<?> cls)
    {

        if(v == null) return ;
        Snackbar snackbar = Snackbar
                .make(v, message, Snackbar.LENGTH_LONG)
                .setAction(MyApplicationBase.getAppContext().getString(R.string.fix_Error), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent( MyApplicationBase.getAppContext(), cls);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MyApplicationBase.getAppContext().startActivity(i);
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }
}
