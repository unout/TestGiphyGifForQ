package a.testappgiphy;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import a.testappgiphy.fragments.GifListFragment;

public class MainActivity extends AppCompatActivity implements Manager.OnUpdateListener {

    private Manager manager = Manager.getInstance();
    private Context context = this;
    private String textToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager.setListener(this);
        manager.callingForTrends(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        final GifListFragment gifListFragment = new GifListFragment();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.searchMessage);
                final EditText input = new EditText(context);
                input.setTextSize(14);
                builder
                        .setView(input)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                textToSearch = input.getText().toString();
                                gifListFragment.setSearchText(textToSearch);
                                manager.callingForSearch(context, textToSearch);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, gifListFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onUpdateFinished(byte resultCode) {
        switch (resultCode) {
            case 1:
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
