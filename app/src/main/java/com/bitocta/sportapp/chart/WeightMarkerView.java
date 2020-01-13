package com.bitocta.sportapp.chart;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.bitocta.sportapp.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeightMarkerView extends MarkerView {

    private TextView tvContent;
    private long referenceTimestamp;  // minimum timestamp in your data set
    private DateFormat mDataFormat;
    private Date mDate;

    public WeightMarkerView(Context context, int layoutResource, long referenceTimestamp) {
        super(context, layoutResource);
        // this markerview only displays a textview
        tvContent =  findViewById(R.id.tvContent);
        this.referenceTimestamp = referenceTimestamp;
        this.mDataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        this.mDate = new Date();

    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        long currentTimestamp = (int)e.getX() + referenceTimestamp;

        tvContent.setText(e.getY() + " at " + getTimedate(currentTimestamp)); // set the entry-value as the display text
        super.refreshContent(e,highlight);
    }

    @Override
    public float getX() {
        return -(getWidth() / 2);
    }


    @Override
    public float getY() {
        return -getHeight();
    }



    private String getTimedate(long timestamp){

        try{

            return mDataFormat.format(timestamp);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}