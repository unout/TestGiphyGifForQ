package a.testappgiphy.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import a.testappgiphy.Manager;
import a.testappgiphy.R;
import a.testappgiphy.adapters.RecyclerAdapter;
import a.testappgiphy.model.GIF;
import a.testappgiphy.support.Constants;

public class GifListFragment extends Fragment implements Manager.OnUpdateListener {

    private RecyclerView mRecyclerView;
    private TextView tvSearch;
    private List<GIF> mGifs;
    private RecyclerAdapter recyclerAdapter;


    public void setSearchText(String toSearch) {
        tvSearch.setText(toSearch);
    }

    public GifListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Manager.getInstance().setListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gif_list, container, false);

        tvSearch = v.findViewById(R.id.tv_search);

        mRecyclerView = v.findViewById(R.id.rv_result);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGifs != null) {
            if (mGifs.size() > 0) {
                recyclerAdapter = new RecyclerAdapter(getActivity(), mGifs);
                mRecyclerView.setAdapter(recyclerAdapter);
            }
        }
    }

    @Override
    public void onUpdateFinished(Constants.Error resultCode) {
        switch (resultCode) {
            case SUCCESS:
                mGifs = Manager.getInstance().getResponse();
                if (mGifs.size() > 0) {
                    recyclerAdapter = new RecyclerAdapter(getActivity(), mGifs);
                    mRecyclerView.setAdapter(recyclerAdapter);
                }
                break;
            case NETWORK_ERROR:
                String network_error = getString(R.string.connection_error);
                Toast.makeText(getActivity(), network_error, Toast.LENGTH_LONG).show();
                break;
            case COMMON_ERROR:
                String common_error = getString(R.string.common_error);
                Toast.makeText(getActivity(), common_error, Toast.LENGTH_LONG).show();
                break;
            case EMPTY_BODY:
                String empty_body = getString(R.string.empty_body);
                Toast.makeText(getActivity(), empty_body, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
