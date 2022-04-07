package org.me.gcu.ivan_todorov_mpd_cw_s1822426;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


/**
 *  @author Ivan Todorov [S1822426]
 *  Reference: Prabeesh R K - Android Studio Tutorial - 66 - AsyncTask with ListView example
 *  https://www.youtube.com/watch?v=2bNBLiqkKlE
 **/

public class RSSEntries extends ListActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemClickListener {

    private ProgressBar progressBarDialog;
    ArrayList<HashMap<String,String>> rssEntryList = new ArrayList<>();
    PullParser PullParser = new PullParser();
    List<Roadworks> rssitemList;
    private RoadworksAdapter dateadapter;
    private static String Title = "title";
    private static String Website = "link";
    private static String Publish_Date = "publishDate";
    private static String Description = "description";
    private static String Coordinates = "georss:point";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadworks);

        ImageButton Main_menu_button = findViewById(R.id.Main_menu_button);
        Main_menu_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent back = new Intent(RSSEntries.this, MainActivity.class);
                back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(back);
            }

        });

        String rss_link = getIntent().getStringExtra("rssURL");
        new LoadRSSFeedItems().execute(rss_link);
        ListView listview = getListView();
        listview.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        Intent traffic = new Intent(getApplicationContext(), MapsActivity.class);
        traffic.putExtra("roadworks", dateadapter.getItem((position)));
        startActivity(traffic);
    }

    public class LoadRSSFeedItems extends AsyncTask <String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarDialog = new ProgressBar(RSSEntries.this, null, android.R.attr.progressBarStyle);
            RelativeLayout relativeLayout = findViewById(R.id.entries_list);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            progressBarDialog.setLayoutParams(layoutParams);
            relativeLayout.addView(progressBarDialog);
        }

        @Override
        protected String doInBackground(String... args){
            String rss_url = args[0];
            rssitemList = PullParser.getRSSFeedItems(rss_url);
            for (final Roadworks item : rssitemList ) {
                if (item.link.toString().equals(""))
                    break;
                HashMap<String, String> map = new HashMap<String, String>();
                String givenDate = item.publishDate.trim();
                SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMMM yyyy - HH:mm");
                map.put(Title, item.title);
                map.put(Website, item.link);
                map.put(Publish_Date, item.publishDate);
                map.put(Description, item.description);
                map.put(Coordinates, item.georss);
                rssEntryList.add(map);
            }
            runOnUiThread(new Runnable() {
                public void run(){

                    dateadapter = new RoadworksAdapter(rssitemList,getApplicationContext());
                    setListAdapter(dateadapter);

                    EditText EntrySearch = (EditText)findViewById(R.id.ItemSearch);
                    EntrySearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            System.out.println(s);
                            dateadapter.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                }
            });
            return null;
        }
        protected void onPostExecute(String args){
            progressBarDialog.setVisibility(View.INVISIBLE);
        }
    }

    public void showDatePicker(View selector){
        DatePickerDialog dataPickerDialog =  createDataPickerDialog(selector);
        dataPickerDialog.show(); }

    public DatePickerDialog createDataPickerDialog(View selector) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(selector.getContext() , this, year, month, day); }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar mycalendar = Calendar.getInstance();
        mycalendar.set(year, month, day);
        this.dateadapter.setSelectedDate(mycalendar .getTime());
        this.dateadapter.getFilter().filter("");
    }
}