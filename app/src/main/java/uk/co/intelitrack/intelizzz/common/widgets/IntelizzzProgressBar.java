package uk.co.intelitrack.intelizzz.common.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import uk.co.intelitrack.intelizzz.R;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class IntelizzzProgressBar extends ProgressBar {
    public IntelizzzProgressBar(Context context) {
        super(context);
        initBackground();
    }

    public IntelizzzProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBackground();
    }

    public IntelizzzProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBackground();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IntelizzzProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initBackground();
    }

    private void initBackground() {
        setBackgroundResource(R.drawable.bg_progress_bar);
    }
}
