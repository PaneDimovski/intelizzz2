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

public class ListViewItemCheckboxBaseAdapter extends BaseAdapter {

    private List<ListViewItemDTO> listViewItemDtoList = null;

    private Context ctx ;

    public ListViewItemCheckboxBaseAdapter(Context ctx, List<ListViewItemDTO> listViewItemDtoList) {
        this.ctx = ctx;
        this.listViewItemDtoList = listViewItemDtoList;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(listViewItemDtoList!=null)
        {
            ret = listViewItemDtoList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int itemIndex) {
        Object ret = null;
        if(listViewItemDtoList!=null) {
            ret = listViewItemDtoList.get(itemIndex);
        }
        return ret;
    }

    @Override
    public long getItemId(int itemIndex) {
        return itemIndex;
    }

    @Override
    public View getView(int itemIndex, View convertView, ViewGroup viewGroup) {


        ListViewItemViewHolder viewHolder = null;

        if(convertView!=null)
        {
            viewHolder = (ListViewItemViewHolder) convertView.getTag();
        }else
        {

            convertView = View.inflate(ctx, R.layout.custom, null);

            CheckBox listItemCheckbox = (CheckBox) convertView.findViewById(R.id.checkMark1);

            TextView listItemText = (TextView) convertView.findViewById(R.id.item_unit_id1);

            viewHolder = new ListViewItemViewHolder(convertView);

            viewHolder.setItemCheckbox(listItemCheckbox);

            viewHolder.setItemTextView(listItemText);

            convertView.setTag(viewHolder);
        }

        ListViewItemDTO listViewItemDto = listViewItemDtoList.get(itemIndex);
        viewHolder.getItemCheckbox().setChecked(listViewItemDto.isChecked());
        viewHolder.getItemTextView().setText(listViewItemDto.getItemText());

        return convertView;
    }
}
