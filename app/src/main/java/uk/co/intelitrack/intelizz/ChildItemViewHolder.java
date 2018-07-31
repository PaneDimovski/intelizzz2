package uk.co.intelitrack.intelizz;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import uk.co.intelitrack.intelizzz.R;

/**
 * Created by Anti on 7/27/2018.
 */

public class ChildItemViewHolder  extends CheckableChildViewHolder {
    private CheckedTextView childCheckedTextView;
    TextView text;



    public ChildItemViewHolder(View itemView) {
        super(itemView);
        childCheckedTextView =
                (CheckedTextView) itemView.findViewById(R.id.checkMark1);

        text = (TextView) itemView.findViewById(R.id.item_unit_id1);
    }

    @Override
    public Checkable getCheckable() {
        return childCheckedTextView;
    }

    public void setChildCheckedTextView(CheckedTextView childCheckedTextView) {
        this.childCheckedTextView = childCheckedTextView;
    }

    public void setArtistName(String artistName) {
        childCheckedTextView.setText(artistName);
    }
}




