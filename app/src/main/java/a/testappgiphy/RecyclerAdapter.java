package a.testappgiphy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import a.testappgiphy.model.GIF;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<GIF> mGifs;
    private LayoutInflater lInflater;

    public RecyclerAdapter(Context context, List<GIF> gifs) {
        mContext = context;
        mGifs = gifs;
        lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = lInflater.inflate(R.layout.gif_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setHolder(mGifs.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mGifs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mGif;

        ViewHolder(View itemView) {
            super(itemView);
            mGif = itemView.findViewById(R.id.vGIF);
        }

        private void setHolder(GIF g) {
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;

            RequestOptions ro = new RequestOptions();
            ro.override(width, width);

            Glide.with(mContext)
                    .asGif()
                    .load("https://media.giphy.com/media/" + g.getId() + "/200w_d.gif")
                    .apply(ro)
                    .into(mGif);
        }

    }
}
