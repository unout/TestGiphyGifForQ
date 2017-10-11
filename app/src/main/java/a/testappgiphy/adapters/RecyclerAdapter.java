package a.testappgiphy.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import a.testappgiphy.support.Constants;
import a.testappgiphy.R;
import a.testappgiphy.fragments.GifFullSizeFragment;
import a.testappgiphy.model.GIF;

import static a.testappgiphy.support.Constants.FULL_GIF;
import static a.testappgiphy.support.Constants.GIF_HOST;

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
        holder.setPosition(position);
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

        private ImageView mGifView;
        private int position;
        void setPosition(int position) {
            this.position = position;
        }

        ViewHolder(final View itemView) {
            super(itemView);
            mGifView = itemView.findViewById(R.id.iv_gif);

            final ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final String[] mActions = mContext.getResources().getStringArray(R.array.dialog_actions);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    final String url = mGifs.get(position).getUrl();
                    final String id = mGifs.get(position).getId();
                    final String gifUrl = GIF_HOST + id + FULL_GIF;
                    builder
                            .setTitle(gifUrl)
                            .setItems(mActions, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i) {
                                        case 0:
                                            ClipData clip = ClipData.newPlainText("url", gifUrl);
                                            clipboard.setPrimaryClip(clip);
                                            break;
                                        case 1:
                                            ((AppCompatActivity) mContext).getFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.fragment_container,
                                                            GifFullSizeFragment.newInstance(gifUrl))
                                                    .addToBackStack(null)
                                                    .commit();
                                            break;
                                        case 2:
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(gifUrl));
                                            mContext.startActivity(browserIntent);
                                            break;

                                    }
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .create()
                            .show();
                    return true;
                }
            });
        }


        private void setHolder(GIF g) {
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;

            RequestOptions ro = new RequestOptions();
            ro.override(width, width);

            Glide.with(mContext)
                    .asGif()
                    .load(GIF_HOST + g.getId() + Constants.SMALL_GIF)
                    .apply(ro)
                    .into(mGifView);
        }

    }
}
