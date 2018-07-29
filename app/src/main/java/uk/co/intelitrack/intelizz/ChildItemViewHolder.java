package uk.co.intelitrack.intelizz;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.TextView;

import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import uk.co.intelitrack.intelizzz.R;

/**
 * Created by Anti on 7/27/2018.
 */

public class ChildItemViewHolder  extends CheckableChildViewHolder {
    private CheckBox childCheckedTextView;
    TextView text;



    public ChildItemViewHolder(View itemView) {
        super(itemView);
        childCheckedTextView =
                (CheckBox) itemView.findViewById(R.id.checkMark1);
        text = (TextView) itemView.findViewById(R.id.item_unit_id1);
    }

    @Override
    public Checkable getCheckable() {
        return childCheckedTextView;
    }

    public void setArtistName(String artistName) {
        childCheckedTextView.setText(artistName);
    }
}




