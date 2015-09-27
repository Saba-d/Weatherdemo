package app.saba.weatherdemo;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class FiveDay extends Activity {

    ListView list;
    /*  String[] itemname ={
              "Safari",
              "Camera",
              "Global",
              "FireFox",
              "UC Browser",

      };*/
    public String[] temp = new String[5];
    public String[] descr = new String[5];
    public String[] precip = new String[5];
    public String[] date1 = new String[5];
    public String[] imgid = new String[5];
    public String[] tempf = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_day);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("city");
            TextView tv1 = (TextView) findViewById(R.id.city);
            tv1.setText(value);
            try {
                List<String> sc = new ArrayList<String>();
                sc = fetchtempC(value);
                temp = sc.toArray(new String[sc.size()]);
                List<String> sc1 = new ArrayList<String>();
                sc1 = fetchdesc(value);
                descr = sc1.toArray(new String[sc1.size()]);

                List<String> far = new ArrayList<String>();
                far = fetchtempF(value);
                descr = far.toArray(new String[far.size()]);


                List<String> sc2 = new ArrayList<String>();
                sc2 = fetchprec(value);
                precip = sc2.toArray(new String[sc2.size()]);
                List<String> sc3 = new ArrayList<String>();
                sc3 = fetchimg(value);
                imgid = sc3.toArray(new String[sc2.size()]);

                List<String> sc4 = new ArrayList<String>();
                sc4 = fetchdate(value);
                date1 = sc4.toArray(new String[sc4.size()]);


                Toast.makeText(this, "" + sc, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                ;
            }
        }
        TextView tv = (TextView) findViewById(R.id.back);
        tv.setText("\u003C");


        CustomListAdapter adapter = new CustomListAdapter(this, temp, descr, precip, imgid, date1, tempf);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem = temp[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void back(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public List<String> fetchtempC(String city) throws Exception {

        String[] desc = new String[5];
        String httpresponse = callURL("http://api.worldweatheronline.com/free/v2/weather.ashx?key=e77763df2a588eec160d762e88dda&q=" + city + "&num_of_days=5&tp=3&format=xml");
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(httpresponse));

        Document dom = builder.parse(src);


        Element docEle = dom.getDocumentElement();
        NodeList nl = docEle.getChildNodes();

        List<String> list1 = new ArrayList<String>();
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);

                    if (el.getNodeName().contains("wea")) {
                        String name = el.getElementsByTagName("maxtempC").item(0).getTextContent();
                        list1.add(name);

                        System.out.println(name);
                           /* String phone = el.getElementsByTagName("phone").item(0).getTextContent();
		                    String email = el.getElementsByTagName("email").item(0).getTextContent();
		                    String area = el.getElementsByTagName("area").item(0).getTextContent();
		                    String city = el.getElementsByTagName("city").item(0).getTextContent();*/
                    }
                }
            }
        }

        return list1;
    }

    public List<String> fetchtempF(String city) throws Exception {

        String[] desc = new String[5];
        String httpresponse = callURL("http://api.worldweatheronline.com/free/v2/weather.ashx?key=e77763df2a588eec160d762e88dda&q=" + city + "&num_of_days=5&tp=3&format=xml");
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(httpresponse));

        Document dom = builder.parse(src);


        Element docEle = dom.getDocumentElement();
        NodeList nl = docEle.getChildNodes();

        List<String> list1 = new ArrayList<String>();
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);

                    if (el.getNodeName().contains("wea")) {
                        String name = el.getElementsByTagName("maxtempF").item(0).getTextContent();
                        list1.add(name);

                        System.out.println(name);
		                   /* String phone = el.getElementsByTagName("phone").item(0).getTextContent();
		                    String email = el.getElementsByTagName("email").item(0).getTextContent();
		                    String area = el.getElementsByTagName("area").item(0).getTextContent();
		                    String city = el.getElementsByTagName("city").item(0).getTextContent();*/
                    }
                }
            }
        }

        return list1;
    }

    public List<String> fetchdesc(String city) throws Exception {

        String[] desc = new String[5];
        String httpresponse = callURL("http://api.worldweatheronline.com/free/v2/weather.ashx?key=e77763df2a588eec160d762e88dda&q=" + city + "&num_of_days=5&tp=3&format=xml");
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(httpresponse));

        Document dom = builder.parse(src);


        Element docEle = dom.getDocumentElement();
        NodeList nl = docEle.getChildNodes();

        List<String> list1 = new ArrayList<String>();
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);

                    if (el.getNodeName().contains("wea")) {
                        String name = el.getElementsByTagName("weatherDesc").item(0).getTextContent();
                        list1.add(name);

                        System.out.println(name);
		                   /* String phone = el.getElementsByTagName("phone").item(0).getTextContent();
		                    String email = el.getElementsByTagName("email").item(0).getTextContent();
		                    String area = el.getElementsByTagName("area").item(0).getTextContent();
		                    String city = el.getElementsByTagName("city").item(0).getTextContent();*/
                    }
                }
            }
        }

        return list1;
    }

    public List<String> fetchdate(String city) throws Exception {

        String[] desc = new String[5];
        String httpresponse = callURL("http://api.worldweatheronline.com/free/v2/weather.ashx?key=e77763df2a588eec160d762e88dda&q=" + city + "&num_of_days=5&tp=3&format=xml");
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(httpresponse));

        Document dom = builder.parse(src);


        Element docEle = dom.getDocumentElement();
        NodeList nl = docEle.getChildNodes();

        List<String> list1 = new ArrayList<String>();
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);

                    if (el.getNodeName().contains("wea")) {
                        String name = el.getElementsByTagName("date").item(0).getTextContent();
                        list1.add(name);

                        System.out.println(name);
		                   /* String phone = el.getElementsByTagName("phone").item(0).getTextContent();
		                    String email = el.getElementsByTagName("email").item(0).getTextContent();
		                    String area = el.getElementsByTagName("area").item(0).getTextContent();
		                    String city = el.getElementsByTagName("city").item(0).getTextContent();*/
                    }
                }
            }
        }

        return list1;
    }


    public List<String> fetchimg(String city) throws Exception {

        String[] desc = new String[5];
        String httpresponse = callURL("http://api.worldweatheronline.com/free/v2/weather.ashx?key=e77763df2a588eec160d762e88dda&q=" + city + "&num_of_days=5&tp=3&format=xml");
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(httpresponse));

        Document dom = builder.parse(src);


        Element docEle = dom.getDocumentElement();
        NodeList nl = docEle.getChildNodes();

        List<String> list1 = new ArrayList<String>();
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);

                    if (el.getNodeName().contains("wea")) {
                        String name = el.getElementsByTagName("weatherIconUrl").item(0).getTextContent();
                        list1.add(name);

                        System.out.println(name);
		                   /* String phone = el.getElementsByTagName("phone").item(0).getTextContent();
		                    String email = el.getElementsByTagName("email").item(0).getTextContent();
		                    String area = el.getElementsByTagName("area").item(0).getTextContent();
		                    String city = el.getElementsByTagName("city").item(0).getTextContent();*/
                    }
                }
            }
        }

        return list1;
    }


    public List<String> fetchprec(String city) throws Exception {

        String[] desc = new String[5];
        String httpresponse = callURL("http://api.worldweatheronline.com/free/v2/weather.ashx?key=e77763df2a588eec160d762e88dda&q=" + city + "&num_of_days=5&tp=3&format=xml");
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(httpresponse));

        Document dom = builder.parse(src);


        Element docEle = dom.getDocumentElement();
        NodeList nl = docEle.getChildNodes();

        List<String> list1 = new ArrayList<String>();
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);

                    if (el.getNodeName().contains("wea")) {
                        String name = el.getElementsByTagName("precipMM").item(0).getTextContent();
                        list1.add(name);

                        System.out.println(name);
		                   /* String phone = el.getElementsByTagName("phone").item(0).getTextContent();
		                    String email = el.getElementsByTagName("email").item(0).getTextContent();
		                    String area = el.getElementsByTagName("area").item(0).getTextContent();
		                    String city = el.getElementsByTagName("city").item(0).getTextContent();*/
                    }
                }
            }
        }

        return list1;
    }

    public static String callURL(String myURL) {
        System.out.println("Requeted URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:" + myURL, e);
        }

        return sb.toString();
    }
}