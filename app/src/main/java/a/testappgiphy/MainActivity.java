package a.testappgiphy;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import a.testappgiphy.model.GIF;

public class MainActivity extends AppCompatActivity implements Manager.OnUpdateListener {

    private Manager manager = Manager.getInstance();
    private Context context = this;
    private TextView tvResult;
    private String textToSearch;
    private RecyclerView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Manager.getInstance().setListener(this);

        tvResult = (TextView) findViewById(R.id.mTextResult);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        mResult = (RecyclerView) findViewById(R.id.RVResult);
        mResult.setHasFixedSize(true);
        mResult.setLayoutManager(new LinearLayoutManager(this));
        manager.callingForTrends(context);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.searchMessage);
                final EditText input = new EditText(context);
                input.setTextSize(14);
                builder.setView(input);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                textToSearch = input.getText().toString();
                                tvResult.setText(textToSearch);
                                manager.callingForSearch(context, textToSearch);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    public void onUpdateFinished(byte resultCode) {
        switch (resultCode) {
            case 1:
                List<GIF> mGifs = Manager.getInstance().getResponse();
                if (mGifs.size() > 0) {
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, mGifs);
                    mResult.setAdapter(recyclerAdapter);
                }
                break;
            case 2:
                String network_error = getString(R.string.connection_error);
                Toast.makeText(this, network_error, Toast.LENGTH_LONG).show();
                break;
            case 3:
                String common_error = getString(R.string.common_error);
                Toast.makeText(this, common_error, Toast.LENGTH_LONG).show();
                break;
            case 4:
                String empty_body = getString(R.string.empty_body);
                Toast.makeText(this, empty_body, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
