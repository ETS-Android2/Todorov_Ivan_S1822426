package org.me.gcu.ivan_todorov_mpd_cw_s1822426;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.ParseException;


/**
 *  @author Ivan Todorov [S1822426]
 **/

public class Roadworks implements Serializable {

    public String title;
    public String description;
    public String link;
    public String georss;
    public String publishDate;
    private Date startDate;
    private Date endDate;


    public Roadworks() {
        this.title = "NULL";
    }

    public Roadworks(String title, String description, String link, String georss, String publishDate){
        this.title = title;
        this.description = description;
        this.link = link;
        this.georss = georss;
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        formatDates(description);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGeorss() {
        return georss;
    }

    public void setGeorss(String georss) {
        this.georss = georss;
    }

    public Double[] getLatLng() {
        String[] latlngsplit = this.georss.split(" ", -1);
        Double[] coordinates = new Double[2];
        coordinates[0] = Double.parseDouble(latlngsplit[0]);
        coordinates[1] = Double.parseDouble(latlngsplit[1]);
        return coordinates;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "roadworks{" +
                "title='" + title +
                ", description='" + description +
                ", link='" + link +
                ", georss='" + georss +
                ", publishDate='" + publishDate +
                '}';
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date stringToDate(String dateString) {
        DateFormat format = new SimpleDateFormat("E, dd MMMM yyyy - HH:mm", Locale.UK);
        List<DateFormat> formatStrings = new ArrayList<>();
        formatStrings.add(format);

        for (DateFormat formatString : formatStrings) {
            try {
                return formatString.parse(dateString.trim());
            } catch (ParseException e) {
            }
        }
        return null;
    }

    public void formatDates(String description){
        String[] dateSplit = description.split("<br />", -1);
        if(dateSplit.length >= 0) {
            String startDate = dateSplit[0].split("Start Date: ")[1];
            String endDate = dateSplit[1].split("End Date: ")[1];
            this.startDate = stringToDate(startDate);
            this.endDate = stringToDate(endDate);
        }
        if(dateSplit.length>=3){
            this.description = dateSplit[2];
        }
    }
}
