package org.me.gcu.ivan_todorov_mpd_cw_s1822426;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 *  @author Ivan Todorov [S1822426]
 *  Reference: CodingWithMitch - Android Beginner Tutorial #11 - ListView Filter and Search Bar
 *  https://www.youtube.com/watch?v=rdQN2U1JJvY
 *
 *  Reference: Android Coding - How to Get Latitude And Longitude in Android Studio | LatitudeAndLongitude
 *  https://www.youtube.com/watch?v=QquRXzJguQM
 **/

public class RoadworksAdapter extends BaseAdapter implements Filterable {

    private List<Roadworks> data;
    private List<Roadworks> filteredData;
    Context Context;
    Date filterDate;
    private LayoutInflater Inflater;

    public void setSelectedDate(Date time) {
        this.filterDate = time;
    }

    private static class ViewHolder {
        TextView title;
        TextView description;
        TextView geoRSS;
        TextView publishDate;
        TextView startDate;
        TextView endDate;
    }

    public RoadworksAdapter(List<Roadworks> data, Context context) {
        this.Context = context;
        this.data = data;
        this.filteredData = data;
        Inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.filteredData.size();
    }

    @Override
    public Roadworks getItem(int position) {
        return this.filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Roadworks dataModel = filteredData.get(position);
        ViewHolder viewHolder;

        viewHolder = new ViewHolder();
        convertView= Inflater.inflate(R.layout.roadworks_list, null);
        viewHolder.title = (TextView) convertView.findViewById(R.id.title);
        viewHolder.description = (TextView) convertView.findViewById(R.id.description);
        viewHolder.geoRSS = (TextView) convertView.findViewById(R.id.georss);
        viewHolder.publishDate = (TextView) convertView.findViewById(R.id.publishDate);
        viewHolder.startDate = (TextView) convertView.findViewById(R.id.startDate);
        viewHolder.endDate = (TextView) convertView.findViewById(R.id.endDate);

        viewHolder.title.setText(dataModel.getTitle());
        viewHolder.description.setText("\uD83D\uDEA7 " + dataModel.getDescription());
        viewHolder.geoRSS.setText("\uD83D\uDCCD Location: " + dataModel.getGeorss());
        viewHolder.publishDate.setText("\uD83D\uDCC5 Publish Date: " + dataModel.getPublishDate());
        viewHolder.startDate.setText("\uD83D\uDD51 Start Date: " + dataModel.getStartDate().toString());
        viewHolder.endDate.setText("\uD83D\uDD5F End Date: " +dataModel.getEndDate().toString());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults entries) {
                filteredData = (List<Roadworks>) entries.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults entries = new FilterResults();
                ArrayList<Roadworks> filteredArrayNames = new ArrayList<>();
                String constraintStr = constraint.toString().toLowerCase();

                if(constraintStr.isEmpty() && filterDate ==null) {
                    filteredData = data;
                }
                else {

                    for (int i = 0; i < data.size(); i++) {
                        String title = data.get(i).getTitle();
                        String desc = data.get(i).getDescription();

                        if (title.toLowerCase().contains(constraintStr.toString())
                                || desc.toLowerCase().contains(constraintStr.toString().toLowerCase())) {
                            if (filterDate != null) {
                                if ((filterDate.after(data.get(i).getStartDate()) || filterDate.equals(data.get(i).getStartDate())) && (filterDate.before(data.get(i).getEndDate()) || filterDate.equals(data.get(i).getEndDate()))) {
                                    filteredArrayNames.add(data.get(i));
                                }
                            }
                            else {
                                filteredArrayNames.add(data.get(i));
                            }
                        }
                    }
                }
                entries.values = filteredArrayNames;
                entries.count = filteredArrayNames.size();
                return entries;
            }
        };
    }
}