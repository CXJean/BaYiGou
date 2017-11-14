package www.yigou.com.bayigou.home.homeAdapter;

import android.content.Context;
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

import static www.yigou.com.bayigou.R.id.mybanner;

/**
 * Created by xue on 2017-11-09.
 */

public class HomeXrecyclerviewAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {



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
        Typetwo,
        Typethree,
        Typefour,
        Typefive, Typesix,
        Type7, Type8,
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
            ((ViewHolderBanner)holder).mybanner.setImageLoader(new BannerGlideImageLoader());
            ((ViewHolderBanner)holder).mybanner.setImages(mlist);
            ((ViewHolderBanner)holder).mybanner.start();
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return Item_Type.TypeBanner.ordinal();
        }
        return 0;
    }



    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolderBanner extends RecyclerView.ViewHolder {

        public Banner mybanner;

        public ViewHolderBanner(View itemView) {
            super(itemView);
            mybanner = (Banner) itemView.findViewById(R.id.mybanner);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
