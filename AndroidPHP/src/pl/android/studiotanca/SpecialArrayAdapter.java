package pl.android.studiotanca;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

@SuppressWarnings("rawtypes")
public class SpecialArrayAdapter extends ArrayAdapter {
	private Context ctx;
	private int layoutResourceId;
	private int[] colors = new int[] { 0x30751c09, 0x30FFFFFF};
	
	public SpecialArrayAdapter(Context context, int layoutResourceId, String[] uczestnicy){
		super(context,layoutResourceId,uczestnicy);
		this.ctx = context;
		this.layoutResourceId = layoutResourceId;
	}
	@Override
	public View getView(int position, View newView, ViewGroup parent){
		View view = super.getView(position, newView, parent);
		int colorPos = position % colors.length;
		view.setBackgroundColor(colors[colorPos]);
		return view;
	}

}
