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

public class ExamsListAdapter extends BaseAdapter {

    private ArrayList<Exam> listData;
    private LayoutInflater layoutInflater;

    public ExamsListAdapter(Context aContext, ArrayList<Exam> listData) {
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

        ExamsListAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_exam, null);
            holder = new ExamsListAdapter.ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.tv_exam_date);
            holder.examName = (TextView) convertView.findViewById(R.id.tv_exam_name);
            convertView.setTag(holder);
        } else {
            holder = (ExamsListAdapter.ViewHolder) convertView.getTag();
        }

        holder.date.setText(listData.get(position).getDate());
        holder.examName.setText(listData.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        TextView date;
        TextView examName;

    }
}
