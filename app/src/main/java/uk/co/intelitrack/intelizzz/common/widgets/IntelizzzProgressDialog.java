package uk.co.intelitrack.intelizzz.common.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uk.co.intelitrack.intelizzz.R;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class IntelizzzProgressDialog extends Dialog {

    public IntelizzzProgressDialog(Context context, int themeResId, boolean cancelable) {
        super(context, themeResId);
        initDialog();
        setCancelable(cancelable);
    }

    private void initDialog() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addContentView(
                inflater.inflate(R.layout.widget_intelizzz_progress_bar, null),
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}

