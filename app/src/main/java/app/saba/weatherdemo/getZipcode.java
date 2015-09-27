package app.saba.weatherdemo;

/**
 * Created by selithey on 9/26/15.
 */

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;

public class getZipcode {
    LocationManager locationManager;
    GPSTracker gps;
    Context c;

    public String getZ() throws IOException{
        gps = new GPSTracker(c);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(c, Locale.getDefault());

            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            final String postalCode = addresses.get(0).getLocality();
            return postalCode;


    }
}
