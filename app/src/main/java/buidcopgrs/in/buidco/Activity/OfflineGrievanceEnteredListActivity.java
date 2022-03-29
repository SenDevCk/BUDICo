package buidcopgrs.in.buidco.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import buidcopgrs.in.buidco.Adaptor.OfflineGrivanceAdapter;
import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.CommonPref;
import buidcopgrs.in.buidco.database.DataBaseHelper;
import buidcopgrs.in.buidco.entity.NewGrivanceData;

public class OfflineGrievanceEnteredListActivity extends AppCompatActivity {
ListView list_offline_gri;
Toolbar toolbar_gd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_grievance_entered_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = (Toolbar) findViewById(R.id.toolbar_off_gri);
            toolbar_gd.setTitle("Grievaence List");
            toolbar_gd.setSubtitle("PGRS (BUIDCo)");
            toolbar_gd.setSubtitleTextColor(getResources().getColor(R.color.white));
            toolbar_gd.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar_gd);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                toolbar_gd.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        ArrayList<NewGrivanceData> grivanceDataArrayList=new DataBaseHelper(OfflineGrievanceEnteredListActivity.this).getNewGrievance(CommonPref.getUserDetails(OfflineGrievanceEnteredListActivity.this).getUserID());
        list_offline_gri=(ListView)findViewById(R.id.list_offline_gri);
        list_offline_gri.setAdapter(new OfflineGrivanceAdapter(OfflineGrievanceEnteredListActivity.this,grivanceDataArrayList));

    }
}
