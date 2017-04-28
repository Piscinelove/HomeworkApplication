package db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.audreycelia.homeworkapp.R;

import java.util.ArrayList;

/**
 * Created by audreycelia on 23.04.17.
 */

public class HomeworksListAdapter extends BaseAdapter {

    private ArrayList<Homework> listData;
    private LayoutInflater layoutInflater;

    public HomeworksListAdapter(Context aContext, ArrayList<Homework> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        HomeworksListAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_homework, null);
            holder = new HomeworksListAdapter.ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.tv_homework_date);
            holder.homeworkName = (TextView) convertView.findViewById(R.id.tv_homework_name);
            convertView.setTag(holder);
        } else {
            holder = (HomeworksListAdapter.ViewHolder) convertView.getTag();
        }

        holder.date.setText(listData.get(position).getDeadline());
        holder.homeworkName.setText(listData.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        TextView date;
        TextView homeworkName;

    }
}
