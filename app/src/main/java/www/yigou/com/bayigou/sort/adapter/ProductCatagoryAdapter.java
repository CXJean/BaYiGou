package www.yigou.com.bayigou.sort.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.sort.bean.ProductCatagory;
import www.yigou.com.bayigou.sort.view.ProGridView;

/**
 * Created by xue on 2017-11-17.
 */

public class ProductCatagoryAdapter extends BaseAdapter{

    // 数据源
    ProductCatagory productCatagory;
    // 上下文
    Context context;
    ArrayList<ProductCatagory.DataBean.ListBean> list;

    public ProductCatagoryAdapter(Context context, ProductCatagory productCatagory) {
        this.context = context;
        this.productCatagory = productCatagory;
    }

    @Override
    public int getCount() {
        Log.d("getCount", "子类适配器: "+productCatagory.getData().size());
        return productCatagory.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return productCatagory.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.d("getItemId", "子类适配器: "+position+"========="+productCatagory.getCode());

        return position;
    }

//    private GetProItemData getProItemData;
//
//    public interface GetProItemData{
//        void getItemProData(String name,String pscid);
//    }
//
//
//    public void getProItemData(GetProItemData ProItemData) {
//        this.getProItemData = ProItemData;
//    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductCatagoryHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sort_right_item, null);
            holder = new ProductCatagoryHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.sort_right_title);
            holder.gridView = (ProGridView) convertView.findViewById(R.id.sort_right_grid);
            convertView.setTag(holder);
        } else {
            holder = (ProductCatagoryHolder) convertView.getTag();
        }
        Log.d("ProductCatagoryAdapter", "-----子类----getView: "+productCatagory.getData().get(position).getName());
        holder.tv.setText(productCatagory.getData().get(position).getName());

        list = new ArrayList<>();

//        for (int j = 0; j < productCatagory.getData().get(position).getList().size(); j++) {
//            List<ProductCatagory.DataBean.ListBean> list = productCatagory.getData().get(position).getList();
//            ProductCatagory.DataBean.ListBean listBean = list.get(j);
//
//            list.add(new ProductCatagory.DataBean.ListBean(listBean.getIcon(),listBean.getName(),listBean.getPcid(),listBean.getPscid()));
//        }
//        //GridView图片
//          /*有图的gridview*/
//        final ProductsAdapter adapter = new ProductsAdapter(list, context);
//        holder.gridView.setAdapter(adapter);
//        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("ProductCatagoryAdapter", "============onItemClick: ============"+list.get(position).getPscid());
//                getItemData.getItemProData(list.get(position).getName(),list.get(position).getPscid()+"");
//            }
//        });

        return convertView;
    }

    class ProductCatagoryHolder {
        TextView tv;
        ProGridView gridView;
    }
}
