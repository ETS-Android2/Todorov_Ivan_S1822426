package org.me.gcu.ivan_todorov_mpd_cw_s1822426;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 *  @author Ivan Todorov [S1822426]
 *  Reference: Mohamed Shehab - Using XML Pull Parser to parse XML data
 *  https://www.youtube.com/watch?v=i7aGM8uy2T0
 **/


public class PullParser {

    private static String Entry = "item";
    private static String Road_Title = "title";
    private static String Additional_Information = "description";
    private static String Website = "link";
    private static String Coordinates = "georss:point";
    private static String Publish_Date= "pubDate";

    public PullParser(){

    }

    public List<Roadworks> getRSSFeedItems(String rss_url) {
        List<Roadworks> rssEntryList = new ArrayList<Roadworks>();
        String rss_feed_xml;

        rss_feed_xml = this.getXmlFromUrl(rss_url);
        if (rss_feed_xml != null) {

            XmlPullParserFactory parserFactory;
            try {
                parserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserFactory.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(new StringReader(rss_feed_xml));

                Roadworks rssTraffic = null;
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String itemRSS = null;
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                                break;
                        case XmlPullParser.START_TAG:
                            itemRSS = parser.getName();
                            if (Entry.equals(itemRSS)) {
                                rssTraffic = new Roadworks();
                            }

                            else if (rssTraffic != null) {
                            if (Road_Title.equals(itemRSS)) {
                                rssTraffic.setTitle(parser.nextText());

                            } else if (Additional_Information.equals(itemRSS))
                            { rssTraffic.setDescription(parser.nextText());

                            } else if (Website.equals(itemRSS)) {
                                rssTraffic.setLink(parser.nextText());

                            } else if (Coordinates.equals(itemRSS)) {
                                rssTraffic.setGeorss(parser.nextText());

                            } else if (Publish_Date.equals(itemRSS)) {
                                rssTraffic.setPublishDate(parser.nextText());
                            }
                        }
                            break;
                        case XmlPullParser.END_TAG:
                            itemRSS = parser.getName();
                            if (itemRSS.equalsIgnoreCase("item") && rssTraffic != null) {
                                rssEntryList.add(rssTraffic);
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            }
            catch (XmlPullParserException | IOException e) {
             }
        }
        return rssEntryList;
    }

    public String getXmlFromUrl(String url){
        String XML = null;
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            XML = EntityUtils.toString(httpEntity);
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return XML;
    }
}
