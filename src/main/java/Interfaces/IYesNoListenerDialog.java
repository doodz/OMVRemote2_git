package Interfaces;

import android.content.Intent;

/**
 * Created by Ividata7 on 08/01/2017.
 */

public interface IYesNoListenerDialog {

    void onYesNoActivityResult(int requestCode, int resultCode, Intent data);
}
