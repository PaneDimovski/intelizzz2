package uk.co.intelitrack.Proba;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Anti on 6/24/2018.
 */

public class ListViewItemViewHolder extends RecyclerView.ViewHolder {

    private CheckBox itemCheckbox;

    private TextView itemTextView;

    public ListViewItemViewHolder(View itemView) {
        super(itemView);
    }

    public CheckBox getItemCheckbox() {
        return itemCheckbox;
    }

    public void setItemCheckbox(CheckBox itemCheckbox) {
        this.itemCheckbox = itemCheckbox;
    }

    public TextView getItemTextView() {
        return itemTextView;
    }

    public void setItemTextView(TextView itemTextView) {
        this.itemTextView = itemTextView;
    }
}