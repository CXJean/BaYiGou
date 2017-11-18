package www.yigou.com.bayigou.sort.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.sort.bean.ProductCatagory;

/**
 * Created by xue on 2017-11-17.
 */

public class ProductsAdapter extends BaseAdapter{


    // 数据源
    ArrayList<ProductCatagory.DataBean.ListBean> lists;
    // 上下文
    Context context;

    public ProductsAdapter(ArrayList<ProductCatagory.DataBean.ListBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductsHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sort_right_grid_item, null);
            holder = new ProductsHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.sort_right_grid_item_tv);

            holder.img = (SimpleDraweeView) convertView.findViewById(R.id.sort_right_grid_item_img);
            convertView.setTag(holder);
        } else {
            holder = (ProductsHolder) convertView.getTag();
        }
        Uri uri = Uri.parse(String.valueOf(lists.get(position).getIcon()));
        holder.img.setImageURI(uri);

        holder.tv.setText(lists.get(position).getName());

        return convertView;
    }

    class ProductsHolder {
        TextView tv;
        SimpleDraweeView img;
    }
}
