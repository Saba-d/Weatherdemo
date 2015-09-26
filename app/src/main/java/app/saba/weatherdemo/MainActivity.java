package app.saba.weatherdemo;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.Intent;

import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class MainActivity extends Activity {

    private ToggleButton toggleButton1, toggleButton2;
    private Button btnDisplay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources r = getResources();
        int picId = r.getIdentifier("thunder", "drawable", "app.saba.weatherdemo");
        LinearLayout image = (LinearLayout) findViewById(R.id.bg);

        image.setBackgroundResource(picId);
     /*  Intent i = new Intent(this, FiveDay.class);
        startActivity(i);*/


    }


}