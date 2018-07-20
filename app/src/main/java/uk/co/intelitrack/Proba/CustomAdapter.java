package uk.co.intelitrack.Proba;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import uk.co.intelitrack.intelizzz.R;

/**
 * Created by Anti on 6/24/2018.
 */

public class CustomAdapter extends BaseAdapter {

    private List<Item> ItemList = null;

    private Context ctx = null;

    public CustomAdapter(Context ctx, List<Item> ItemList) {
        this.ctx = ctx;
        this.ItemList = ItemList;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(ItemList!=null)
        {
            ret = ItemList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int itemIndex) {
        Object ret = null;
        if(ItemList!=null) {
            ret = ItemList.get(itemIndex);
        }
        return ret;
    }

    @Override
    public long getItemId(int itemIndex) {
        return itemIndex;
    }

    @Override
    public View getView(int itemIndex, View convertView, ViewGroup viewGroup) {

        ItemViewHolder viewHolder = null;

        if(convertView!=null)
        {
            viewHolder = (ItemViewHolder) convertView.getTag();
        }else
        {
            convertView = View.inflate(ctx, R.layout.custom, null);

            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkMark5);
            TextView listItemText = (TextView) convertView.findViewById(R.id.item_unit_id5);
            TextView tetx = (TextView) convertView.findViewById(R.id.tekst5);
            TextView textId = (TextView) convertView.findViewById(R.id.item_number5);


            viewHolder = new ItemViewHolder(convertView);

            viewHolder.setItemCheckbox(checkbox);

            viewHolder.setItemTextView(listItemText);

            convertView.setTag(viewHolder);
        }

        Item listViewItemDto = ItemList.get(itemIndex);
        viewHolder.getItemCheckbox().setChecked(listViewItemDto.isChecked());
        viewHolder.getItemTextView().setText(listViewItemDto.getItemText());

        return convertView;
    }
}