package org.me.gcu.ivan_todorov_mpd_cw_s1822426;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import java.util.ArrayList;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageButton;

/**
 *  @author Ivan Todorov [S1822426]
 **/

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ArrayList<String> TrafficScotlandRSS = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton Roadworks_button1 = findViewById(R.id.Roadworks_button1);
        Roadworks_button1.setOnClickListener(this);
        ImageButton Roadworks_button2 = findViewById(R.id.Roadworks_button2);
        Roadworks_button2.setOnClickListener(this);

        TrafficScotlandRSS.add("https://trafficscotland.org/rss/feeds/roadworks.aspx");
        TrafficScotlandRSS.add("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx");
    }

    @Override
    public void onClick(View view){
        Intent traffic = new Intent(getApplicationContext(), RSSEntries.class);
        switch(view.getId()){
            case R.id.Roadworks_button1:
                traffic.putExtra("rssURL", TrafficScotlandRSS.get(0));
                startActivity(traffic);
                break;
            case R.id.Roadworks_button2:
                traffic.putExtra("rssURL", TrafficScotlandRSS.get(1));
                startActivity(traffic);
                break;
        }
    }
}