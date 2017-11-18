package www.yigou.com.bayigou.home.homeAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import es.dmoral.toasty.Toasty;
import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.home.bean.HomeBean;
import www.yigou.com.bayigou.home.bean.Pid;
import www.yigou.com.bayigou.home.view.GoodsInfoActivity;
import www.yigou.com.bayigou.home.view.OnItemClickListener;

/**
 * Created by xue on 2017-11-14.
 */

public class ItemTitleAdapter extends RecyclerView.Adapter<ItemTitleAdapter.staggerView> {

    int type;
    Context context;
    HomeBean.DataBean DataBean;
    private ItemTitleAdapter.staggerView staggerView;

    private OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(OnItemClickListener mItemClickListener){
        this.mItemClickListener=mItemClickListener;
    }


    public ItemTitleAdapter(int type, Context context, HomeBean.DataBean dataBean) {
        this.type = type;
        this.context = context;
        this.DataBean = dataBean;
    }

    @Override
    public staggerView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (type<=5){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_title, null, false);
            staggerView = new staggerView(view);
            return staggerView;
        }
        return staggerView;
    }

    @Override
    public void onBindViewHolder(final staggerView holder, int position) {
            if (type==1){
                holder.itemFenTitle.setText(DataBean.getAd5().get(position).getTitle());

                ImageLoader.getInstance().displayImage(DataBean.getAd5().get(position).getImage(),holder.itemFenImg);
//                Uri uri = Uri.parse(DataBean.getAd5().get(position).getImage());
//                holder.itemFenImg.setImageURI(uri);
            }else if(type==2){
                //只有图片
                ImageLoader.getInstance().displayImage(DataBean.getActivityInfo().getActivityInfoList().get(position).getActivityImg(),holder.itemFenImg);

//                Uri uri = Uri.parse(DataBean.getActivityInfo().getActivityInfoList().get(position).getActivityImg());
//                holder.itemFenImg.setImageURI(uri);
            }else if (type==3){
                holder.itemFenTitle.setText(DataBean.getSubjects().get(0).getGoodsList().get(position).getGoods_name());

                ImageLoader.getInstance().displayImage(DataBean.getSubjects().get(0).getGoodsList().get(position).getGoods_img(),holder.itemFenImg);
            }else if (type==4){
                holder.itemFenTitle.setText(DataBean.getSubjects().get(1).getGoodsList().get(position).getGoods_name());

                ImageLoader.getInstance().displayImage(DataBean.getSubjects().get(1).getGoodsList().get(position).getGoods_img(),holder.itemFenImg);
            }else if (type==5){
                holder.itemFenTitle.setText(DataBean.getDefaultGoodsList().get(position).getGoods_name());

                ImageLoader.getInstance().displayImage(DataBean.getDefaultGoodsList().get(position).getGoods_img(),holder.itemFenImg);
            }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.setOnItemClick(holder.itemView,holder.getLayoutPosition());
            }
        });

        ItemTitleAdapter.this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void setOnItemClick(View view, int position) {
                Toasty.success(context,"第几个ItemTitleAdapter"+position, Toast.LENGTH_SHORT).show();
                //发送粘性事件
                EventBus.getDefault().postSticky(new Pid(position+1+""));
                Intent intent = new Intent(context,GoodsInfoActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (type==1){
            return DataBean.getAd5().size();
        }else if(type==2){
            return DataBean.getActivityInfo().getActivityInfoList().size();
        }else if(type==3){//第一个主题列表
            return DataBean.getSubjects().get(0).getGoodsList().size();
        }else if(type==4){//第二个主题列表
            return DataBean.getSubjects().get(1).getGoodsList().size();
        }else if(type==5){//喜欢主题列表
            return DataBean.getDefaultGoodsList().size();
        }
        return 0;

    }
    public static class staggerView extends RecyclerView.ViewHolder {
        //此处使用butterknife报空指针
//        @BindView(R.id.item_fen_img)
//        SimpleDraweeView itemFenImg;
//        @BindView(R.id.item_fen_title)
//        TextView itemFenTitle;
        TextView itemFenTitle;
//        private final SimpleDraweeView itemFenImg;
        public ImageView itemFenImg;

        public staggerView(View view) {
            super(view);
            itemFenImg = (ImageView) view.findViewById(R.id.item_fen_img);

//            itemFenImg = (SimpleDraweeView) view.findViewById(R.id.item_fen_img);
            itemFenTitle = (TextView) view.findViewById(R.id.item_fen_title);
        }
    }
}
