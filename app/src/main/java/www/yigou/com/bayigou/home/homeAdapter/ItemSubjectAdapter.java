package www.yigou.com.bayigou.home.homeAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import es.dmoral.toasty.Toasty;
import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.home.bean.HomeBean;
import www.yigou.com.bayigou.home.view.OnItemClickListener;

/**
 * Created by xue on 2017-11-14.
 */

public class ItemSubjectAdapter extends RecyclerView.Adapter<ItemSubjectAdapter.staggerView> {

    int type;
    Context context;
    HomeBean.DataBean DataBean;
    private ItemSubjectAdapter.staggerView staggerView;

    public OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(OnItemClickListener mItemClickListener){
        this.mItemClickListener=mItemClickListener;
    }

    public ItemSubjectAdapter(int type, Context context, HomeBean.DataBean dataBean) {
        this.type = type;
        this.context = context;
        this.DataBean = dataBean;
    }

    @Override
    public staggerView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (type<=3){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_sub, null, false);
            staggerView = new staggerView(view);
            return staggerView;
        }
        return staggerView;
    }

    @Override
    public void onBindViewHolder(final staggerView holder, int position) {
            if (type==1){
                holder.subTitle.setText(DataBean.getSubjects().get(0).getTitle());

                ImageLoader.getInstance().displayImage(DataBean.getSubjects().get(0).getImage(),holder.subImg);
//                Uri uri = Uri.parse(DataBean.getAd5().get(position).getImage());
//                holder.itemFenImg.setImageURI(uri);

            }else if(type==2){

                holder.subTitle.setText(DataBean.getSubjects().get(1).getTitle());

                ImageLoader.getInstance().displayImage(DataBean.getSubjects().get(1).getImage(),holder.subImg);
            }else if(type==3){

                holder.subTitle.setText("猜你喜欢");

            }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.setOnItemClick(holder.itemView,holder.getLayoutPosition());
            }
        });

        ItemSubjectAdapter.this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void setOnItemClick(View view, int position) {
                Toasty.success(context,"第几个ItemSubjectAdapter"+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (type==1){
            return 1;
        }
        else if(type==2){
            return 1;
        }else if(type==3){
            return 1;
        }
        return 0;

    }
    public static class staggerView extends RecyclerView.ViewHolder {
        //此处使用butterknife报空指针
//        @BindView(R.id.item_fen_img)
//        SimpleDraweeView itemFenImg;
//        @BindView(R.id.item_fen_title)
//        TextView itemFenTitle;
        TextView subTitle;
//        private final SimpleDraweeView itemFenImg;
        public ImageView subImg;

        public staggerView(View view) {
            super(view);
            subImg = (ImageView) view.findViewById(R.id.sub_img);

//            itemFenImg = (SimpleDraweeView) view.findViewById(R.id.item_fen_img);
            subTitle = (TextView) view.findViewById(R.id.sub_title);
        }
    }
}
