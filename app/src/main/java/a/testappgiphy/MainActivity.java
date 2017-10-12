package a.testappgiphy;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import a.testappgiphy.fragments.GifListFragment;
import a.testappgiphy.support.Constants;

public class MainActivity extends AppCompatActivity implements Manager.OnUpdateListener {

    private String textToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Manager manager = Manager.getInstance();
        manager.setListener(this);
        manager.callingForTrends(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        final GifListFragment gifListFragment = new GifListFragment();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.searchMessage);
                final EditText input = new EditText(MainActivity.this);
                input.setTextSize(getResources().getDimension(R.dimen.text_size));
                builder
                        .setView(input)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                textToSearch = input.getText().toString();
                                gifListFragment.setSearchText(textToSearch);
                                Manager.getInstance().callingForSearch(MainActivity.this, textToSearch);
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
    public void onUpdateFinished(Constants.Error resultCode) {
        switch (resultCode) {
            case SUCCESS:
                break;
            case NETWORK_ERROR:
                String network_error = getString(R.string.connection_error);

                Toast.makeText(this, network_error, Toast.LENGTH_LONG).show();
                break;
            case COMMON_ERROR:
                String common_error = getString(R.string.common_error);
                Toast.makeText(this, common_error, Toast.LENGTH_LONG).show();
                break;
            case EMPTY_BODY:
                String empty_body = getString(R.string.empty_body);
                Toast.makeText(this, empty_body, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
