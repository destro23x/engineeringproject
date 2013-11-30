package pl.android.studiotanca;

import java.util.HashMap;
import java.util.List;

import com.example.androidphp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

public class SpecialAdapter extends SimpleAdapter {
	private int[] colors = new int[] { 0x30751c09, 0x30FFFFFF};
	private Context ctx;
	
	public SpecialAdapter(Context context, List<HashMap<String,String>> items, int resource, String[] from, int[] to){
		super(context,items,resource,from,to);
		ctx = context;
	}
	
	@Override
	public View getView(int position, View newView, ViewGroup parent){
		View view = super.getView(position, newView, parent);
		int colorPos = position % colors.length;
		view.setBackgroundColor(colors[colorPos]);
		return view;
	}
	/*@Override
	public View getView(int position, View newView, ViewGroup parent) {
        View v = super.getView(position, newView, parent);
        
        // First let's verify the convertView is not null
        
        // This a new view we inflate the new layout
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activity_allstudents_items, null);
                
        if ( position % 2 == 0)
        	v.setBackgroundResource(R.drawable.listview_selector_even);
        else
        	v.setBackgroundResource(R.drawable.listview_selector_odd);
        
        return v;
	}*/
}
