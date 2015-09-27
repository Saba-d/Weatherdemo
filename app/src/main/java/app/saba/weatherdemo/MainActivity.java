package app.saba.weatherdemo;
/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.List;
import java.util.Locale;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.location.Address;
import android.location.Geocoder;

import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.io.IOException;
import java.util.logging.Logger;

import android.widget.TextView;

import app.saba.weatherdemo.R;

import android.os.StrictMode;


public class MainActivity extends Activity {
    LocationManager locationManager;
    GPSTracker gps;
    public String[] sw;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Toast.makeText(this, "\nOutput: \n" + callURL("http://api.worldweatheronline.com/free/v2/weather.ashx?key=e77763df2a588eec160d762e88dda&q=60626&num_of_days=5&tp=3&format=xml"), Toast.LENGTH_LONG).show();

        try {
            sw = fetchData();

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LinearLayout layout = (LinearLayout) findViewById(R.id.bg);
        try {
            URL url = new URL(sw[4]);
            Bitmap myImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());


            //BitmapDrawable(obj) convert Bitmap object into drawable object.
            Drawable dr = new BitmapDrawable(myImage);
            layout.setBackgroundDrawable(dr);

        } catch (Exception e) {
            e.printStackTrace();
        }


        //layout.setBackgroundResource(R.drawable.thunder);
        final TextView temperature = (TextView) findViewById(R.id.temp);
        TextView description = (TextView) findViewById(R.id.desc);
        TextView precipitation = (TextView) findViewById(R.id.prec);
        TextView cel = (TextView) findViewById(R.id.cel);
        TextView far = (TextView) findViewById(R.id.far);
        cel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                temperature.setText(sw[0] + "\u2103");

            }
        });
        far.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                temperature.setText(sw[1] + "\u2109");

            }
        });


        ImageView icon = (ImageView) findViewById(R.id.icon);
        try {
            URL url = new URL(sw[4]);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            icon.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
        temperature.setText(sw[1] + "\u2109"); //u2103C, u2109F
        description.setText(sw[2]);
        precipitation.setText(sw[3] + "\u0025");
    }
    // Toast.makeText(this,"sw:"+sw[1], Toast.LENGTH_SHORT).show();
    //System.out.println("\nOutput: \n" + callURL("http://api.worldweatheronline.com/free/v2/weather.ashx?key=e77763df2a588eec160d762e88dda&q=60626&num_of_days=5&tp=3&format=xml"));

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

    public String[] fetchData() throws ParserConfigurationException, SAXException, IOException {
        String[] returnData = new String[5];
        String zipcode = getZip();
        String httpresponse = callURL("http://api.worldweatheronline.com/free/v2/weather.ashx?key=e77763df2a588eec160d762e88dda&q=" + zipcode + "&num_of_days=1&tp=3&format=xml");
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(httpresponse));

            Document doc = builder.parse(src);
            String temp_C = doc.getElementsByTagName("temp_C").item(0).getTextContent();
            String temp_F = doc.getElementsByTagName("temp_F").item(0).getTextContent();
            String description = doc.getElementsByTagName("weatherDesc").item(0).getTextContent();
            String precipitation = doc.getElementsByTagName("precipMM").item(0).getTextContent();
            String iconurl = doc.getElementsByTagName("weatherIconUrl").item(0).getTextContent();
            returnData[0] = temp_C;
            returnData[1] = temp_F;
            returnData[2] = description;
            returnData[3] = precipitation;
            returnData[4] = iconurl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnData;
    }

    public void getWeather(View v) {

        EditText mEdit = (EditText) findViewById(R.id.thishere);
        String val = mEdit.getText().toString();
        Intent i = new Intent(this, FiveDay.class);
        i.putExtra("city", val);
        startActivity(i);
    }


    public String getZip() throws IOException {
        String postalCode;
        gps = new GPSTracker(this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1);
        postalCode = addresses.get(0).getPostalCode();

        return postalCode;
    }

}

