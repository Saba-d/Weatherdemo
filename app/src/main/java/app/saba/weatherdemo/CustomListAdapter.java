package app.saba.weatherdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] imgid;
    private final String[] desc;
    private final String[] prec;
    private final String[] date;
    private final String[] tempf;

    public CustomListAdapter(Activity context, String[] itemname, String[]desc, String[] prec, String[] imgid, String[] date, String[] tempf) {
        super(context, R.layout.activity_custom_list_adapter, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.prec=prec;
        this.desc=desc;
        this.date=date;
        this.tempf=tempf;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_custom_list_adapter, null, true);

        TextView description = (TextView) rowView.findViewById(R.id.desc);
        TextView precipitation = (TextView) rowView.findViewById(R.id.prec);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.day);

        TextView date1 = (TextView) rowView.findViewById(R.id.temp);


        try {
            URL url = new URL(imgid[position]);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageView.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }



date1.setText(date[position]);
        extratxt.setText(itemname[position]);
        description.setText(desc[position]);
        precipitation.setText(prec[position]);
        return rowView;

    };
}