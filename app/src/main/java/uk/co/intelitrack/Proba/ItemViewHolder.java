package uk.co.intelitrack.Proba;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Anti on 6/24/2018.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public CheckBox itemCheckbox;
    private TextView IdTextView;
    private TextView itemTextView;
    private TextView IdTextView2;

    public TextView getIdTextView2() {
        return IdTextView2;
    }

    public void setIdTextView2(TextView idTextView2) {
        IdTextView2 = idTextView2;
    }

    public ItemViewHolder(View itemView) {
        super(itemView);
    }

    public TextView getIdTextView() {
        return IdTextView;
    }

    public void setIdTextView(TextView idTextView) {
        IdTextView = idTextView;
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