package com.trust.trustbluetooth.ble.central;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trust.trustbluetooth.R;

import java.util.List;

/*
 * 为ListView提供内容的自定义Adapter
 * 卖萌专用..其实用ArrayAdapter就能完成功能了
 */

public class MyAdapter extends BaseAdapter {

	private Context context;
	private List<BluetoothDevice> list;

	public MyAdapter(Context c, List<BluetoothDevice> l) {
		context = c;
		list = l;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout=(LinearLayout) inflater.inflate(R.layout.list_item, null);
		TextView name=(TextView)layout.findViewById(R.id.name);
		TextView address=(TextView)layout.findViewById(R.id.address);
		name.setTextSize(20f);
		name.setText(list.get(arg0).getName());
		address.setText(list.get(arg0).getAddress());
		return layout;
	}
}
