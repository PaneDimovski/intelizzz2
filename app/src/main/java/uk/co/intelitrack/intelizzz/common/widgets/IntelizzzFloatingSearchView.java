package uk.co.intelitrack.intelizzz.common.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;
import android.view.WindowManager;

import com.arlib.floatingsearchview.FloatingSearchView;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class IntelizzzFloatingSearchView extends FloatingSearchView {
    private Activity mHostActivity;

    public IntelizzzFloatingSearchView(Context context) {
        super(context);

        mHostActivity = getHostActivity();
        if (mHostActivity != null) {
            mHostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    public IntelizzzFloatingSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mHostActivity = getHostActivity();
        if (mHostActivity != null) {
            mHostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    private Activity getHostActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}