package www.yigou.com.bayigou.home.homeAdapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;
import www.yigou.com.bayigou.R;
import www.yigou.com.bayigou.home.bean.HomeBean;
import www.yigou.com.bayigou.home.utils.BannerGlideImageLoader;

/**
 * Created by xue on 2017-11-09.
 */

public class HomeXrecyclerviewAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {



    //上下文环境
    private Context context;
    private HomeBean homeBean;
    private ArrayList mlist;


    public HomeXrecyclerviewAdapter(Context context, HomeBean homeBean) {
        this.context = context;
        this.homeBean = homeBean;
    }

    /*枚举类型*/
    private enum Item_Type {
        TypeBanner,
        TypeTitle,
        TypeActiviInfo,
        TypeSubject1,
        TypeSubject1Info,
        TypeSubject2,
        TypeSubject2Info,
        Type8,
        Type9
    }

    /**
     * 创建ViewHolder
     *
     * @param viewType :不同ItemView的类型
     */
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Item_Type.TypeBanner.ordinal()) {
            View view = LayoutInflater.from(context).inflate(R.layout.home_banner, null);
            ViewHolderBanner viewHolderBanner = new ViewHolderBanner(view);
            return viewHolderBanner;
        } else if (viewType == Item_Type.TypeTitle.ordinal()) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recy_item, null);
            ViewHolderTitle viewHolderTitle = new ViewHolderTitle(mView);
            return viewHolderTitle;
        }else if (viewType == Item_Type.TypeActiviInfo.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recy_item, null);
            ViewHolderActiInfo viewHolderActiInfo = new ViewHolderActiInfo(view);
            return viewHolderActiInfo;
        }else if (viewType == Item_Type.TypeSubject1.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recy_item, null);
            ViewHolderSubject1 viewHolderSubject1 = new ViewHolderSubject1(view);
            return viewHolderSubject1;
        }else if (viewType == Item_Type.TypeSubject1Info.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recy_item, null);
            ViewHolderSubject1Info viewHolderSubject1info = new ViewHolderSubject1Info(view);
            return viewHolderSubject1info;
        }else if (viewType == Item_Type.TypeSubject2.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recy_item, null);
            ViewHolderSubject2 viewHolderSubject2 = new ViewHolderSubject2(view);
            return viewHolderSubject2;
        }else if (viewType == Item_Type.TypeSubject2Info.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recy_item, null);
            ViewHolderSubject2Info viewHolderSubject2info = new ViewHolderSubject2Info(view);
            return viewHolderSubject2info;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(XRecyclerView.ViewHolder holder, int position) {
        ///banner轮播图
        if (holder instanceof ViewHolderBanner) {
            mlist = new ArrayList();
            for (int i = 0; i < homeBean.getData().getAd1().size(); i++) {
                mlist.add(homeBean.getData().getAd1().get(i).getImage());
            }
            //设置图片加载器
            ((ViewHolderBanner) holder).mybanner.setImageLoader(new BannerGlideImageLoader());
            ((ViewHolderBanner) holder).mybanner.setImages(mlist);
            ((ViewHolderBanner) holder).mybanner.start();
        } else if (holder instanceof ViewHolderTitle) {
            //竖列的标题(每日签到)
            // 网格布局
            ((ViewHolderTitle) holder).homeItemRcview.setLayoutManager(
                    new GridLayoutManager(context, 4));
            ((ViewHolderTitle) holder).homeItemRcview.setAdapter(new ItemTitleAdapter(1,context, homeBean.getData()));
        } else if (holder instanceof ViewHolderActiInfo) {
            //横向的搭配减价图片
            ((ViewHolderActiInfo) holder).homeItemRcview.setLayoutManager(
                    new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true));
            ((ViewHolderActiInfo) holder).homeItemRcview.setAdapter(new ItemTitleAdapter(2,context, homeBean.getData()));
        }else if (holder instanceof ViewHolderSubject1) {
            //第一个专题
            ((ViewHolderSubject1) holder).homeItemRcview.setLayoutManager(
                    new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true));
            ((ViewHolderSubject1) holder).homeItemRcview.setAdapter(new ItemSubjectAdapter(1,context, homeBean.getData()));
        }else if (holder instanceof ViewHolderSubject1Info) {
            //第一个专题横向图片列表
            ((ViewHolderSubject1Info) holder).homeItemRcview.setLayoutManager(
                    new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true));
            ((ViewHolderSubject1Info) holder).homeItemRcview.setAdapter(new ItemTitleAdapter(3,context, homeBean.getData()));
        }else if (holder instanceof ViewHolderSubject2) {
            //第二个专题
            ((ViewHolderSubject2) holder).homeItemRcview.setLayoutManager(
                    new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true));
            ((ViewHolderSubject2) holder).homeItemRcview.setAdapter(new ItemSubjectAdapter(2,context, homeBean.getData()));
        }else if (holder instanceof ViewHolderSubject2Info) {
            //第二个专题横向图片列表
            ((ViewHolderSubject2Info) holder).homeItemRcview.setLayoutManager(
                    new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true));
            ((ViewHolderSubject2Info) holder).homeItemRcview.setAdapter(new ItemTitleAdapter(4,context, homeBean.getData()));
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return Item_Type.TypeBanner.ordinal();
        } else if (position == 1) {
            return Item_Type.TypeTitle.ordinal();
        }else if (position == 2) {
            return Item_Type.TypeActiviInfo.ordinal();
        }else if (position == 3) {
            return Item_Type.TypeSubject1.ordinal();
        }else if (position == 4) {
            return Item_Type.TypeSubject1Info.ordinal();
        }else if (position == 5) {
            return Item_Type.TypeSubject2.ordinal();
        }else if (position == 6) {
            return Item_Type.TypeSubject2Info.ordinal();
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    //轮播图
    class ViewHolderBanner extends RecyclerView.ViewHolder {

        public Banner mybanner;

        public ViewHolderBanner(View itemView) {
            super(itemView);
            mybanner = (Banner) itemView.findViewById(R.id.mybanner);
        }
    }

    //每日签到
    class ViewHolderTitle extends RecyclerView.ViewHolder {
//        @BindView(R.id.home_item_rcview)
//        RecyclerView homeItemRcview;
        public RecyclerView homeItemRcview;

        public ViewHolderTitle(View itemView) {
            super(itemView);
            homeItemRcview = (RecyclerView) itemView.findViewById(R.id.home_item_rcview);
        }
    }
    //搭配减价
    class ViewHolderActiInfo extends RecyclerView.ViewHolder {

        public RecyclerView homeItemRcview;

        public ViewHolderActiInfo(View itemView) {
            super(itemView);
            homeItemRcview = (RecyclerView) itemView.findViewById(R.id.home_item_rcview);
        }
    }
    //第一个热门专题
    class ViewHolderSubject1 extends RecyclerView.ViewHolder {

        public RecyclerView homeItemRcview;

        public ViewHolderSubject1(View itemView) {
            super(itemView);
            homeItemRcview = (RecyclerView) itemView.findViewById(R.id.home_item_rcview);
        }
    }
    //第一个热门专题的横向列表
    class ViewHolderSubject1Info extends RecyclerView.ViewHolder {

        public RecyclerView homeItemRcview;

        public ViewHolderSubject1Info(View itemView) {
            super(itemView);
            homeItemRcview = (RecyclerView) itemView.findViewById(R.id.home_item_rcview);
        }
    }
    //第二个热门专题
    class ViewHolderSubject2 extends RecyclerView.ViewHolder {

        public RecyclerView homeItemRcview;

        public ViewHolderSubject2(View itemView) {
            super(itemView);
            homeItemRcview = (RecyclerView) itemView.findViewById(R.id.home_item_rcview);
        }
    }
    //第二个专题的横向列表
    class ViewHolderSubject2Info extends RecyclerView.ViewHolder {

        public RecyclerView homeItemRcview;

        public ViewHolderSubject2Info(View itemView) {
            super(itemView);
            homeItemRcview = (RecyclerView) itemView.findViewById(R.id.home_item_rcview);
        }
    }

}
