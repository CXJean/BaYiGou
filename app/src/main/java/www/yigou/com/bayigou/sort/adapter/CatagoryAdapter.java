package www.yigou.com.bayigou.sort.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.sort.bean.Catagory;

/**
 * Created by xue on 2017-11-17.
 */

public class CatagoryAdapter extends BaseAdapter{

    Context context;
    Catagory catagory;

    public CatagoryAdapter(Context context, Catagory catagory) {
        this.context = context;
        this.catagory = catagory;
    }

    @Override
    public int getCount() {
        return catagory.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return catagory.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*接口回调*/
    public interface ClickCaName{
        void ClickCaname(String cid);
    }
    private ClickCaName clickCaName;

    public void setClickCaName(ClickCaName clickCaName) {
        this.clickCaName = clickCaName;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CatagoryHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.sort_left_item,null);
            holder = new CatagoryHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.sort_left_tv);
            convertView.setTag(holder);
        }else{
            holder = (CatagoryHolder) convertView.getTag();
        }
        Log.d("CatagoryAdapter", "CatagoryAdapter----------------getView: "+catagory.getData().get(position).getName());
        holder.tv.setText(catagory.getData().get(position).getName());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CatagoryAdapter", "CatagoryAdapter----------------getView: "+catagory.getData().get(position).getCid());
                clickCaName.ClickCaname(catagory.getData().get(position).getCid());
            }
        });
        return convertView;
    }
    class CatagoryHolder{
        TextView tv;
    }
}
