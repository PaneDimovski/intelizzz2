package uk.co.intelitrack.intelizzz.common.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.widgets.IntelizzzProgressDialog;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class DialogUtils {

    public static AlertDialog showAlertDialog(Activity activity, CharSequence title, CharSequence message) {
        return new AlertDialog.Builder(activity, R.style.Intelizzz_AlertDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    public static AlertDialog showAlertDialog(Activity activity, CharSequence title, CharSequence message,
                                              DialogInterface.OnClickListener yesListener) {
        return new AlertDialog.Builder(activity, R.style.Intelizzz_AlertDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, yesListener)
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public static IntelizzzProgressDialog getProgressBarDialog(Activity activity) {
        return new IntelizzzProgressDialog(
                activity,
                R.style.Intelizzz_ProgressBarDialogTheme,
                false);
    }
}
