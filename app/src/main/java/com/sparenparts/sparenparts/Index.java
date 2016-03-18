package com.sparenparts.sparenparts;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class Index extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
public
    Context context = this;
    TabHost mTabHost;
    static float density;
    static float textSize=0;
    static String allCategoryURL="http://api.sparenparts.com/Service1.asmx/LoadCategory";
    static int timeout=10000;
    static int layoutHeight=0;
    static int layoutWidth=0;
    static int navBarSize=0;
    String [][]allCategoryDetails=new String[19][100];
    public  ArrayList<ListModel> allCategoryDetailsArr = new ArrayList<ListModel>();
    public  Index CustomListView = this;
    List<String> categoriesNames = new ArrayList<String>();
    int isLayoutReady=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        workTabView();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean workTabView()
    {
        //Set Tab Names
        mTabHost = (TabHost)findViewById(R.id.indexTab);
        mTabHost.setup();
        TabHost.TabSpec spec=mTabHost.newTabSpec("tab1");
        spec.setContent(R.id.listViewTab1);
        spec.setIndicator("SHOP BY CATEGORIES");
        mTabHost.addTab(spec);
        spec=mTabHost.newTabSpec("tab2");
        spec.setContent(R.id.listViewTab2);
        spec.setIndicator("SHOW BY BRAND");
        mTabHost.addTab(spec);
        mTabHost.setCurrentTab(0);


        int currentTab = mTabHost.getCurrentTab();
        TabWidget widget = mTabHost.getTabWidget();
        //This is initial house keeping.
        for (int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);
            // Look for the title view to ensure this is an indicator and not a divider.
            TextView tv = (TextView) v.findViewById(android.R.id.title);
            textSize=(tv.getTextSize())/density;
            tv.setTextSize(textSize+textSize*.15f);
            if (tv == null) {
                continue;
            }
            if (currentTab == i)
                v.setBackgroundResource(R.drawable.tab_switch_style);
            else {
                v.setBackgroundResource(R.drawable.tab_switch_blank_style);
            }
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int currentTab = mTabHost.getCurrentTab();
                TabWidget widget = mTabHost.getTabWidget();
                for (int i = 0; i < widget.getChildCount(); i++) {
                    View v = widget.getChildAt(i);
                    // Look for the title view to ensure this is an indicator and not a divider.
                    TextView tv = (TextView) v.findViewById(android.R.id.title);
                    if (tv == null) {
                        continue;
                    }
                    if (currentTab == i)
                        v.setBackgroundResource(R.drawable.tab_switch_style);
                    else {
                        v.setBackgroundResource(R.drawable.tab_switch_blank_style);
                    }
                }
            }
        });

        //new fillSHOPBYCATEGORY().execute();
        return(true);
    }

    // ping AsyncTask
    private class fillSHOPBYCATEGORY extends AsyncTask<Void, Void, Void> {
        int JSOUPEXCEPTIONS=-1;
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
               try {
                String allCategoryJSON = Jsoup.connect(allCategoryURL).ignoreContentType(true).timeout(timeout).execute().body();


                JSONObject  jsonRootObject = new JSONObject(allCategoryJSON);
                JSONArray jsonArray = jsonRootObject.optJSONArray("AllCategoryDetails");
                String data="";
                //Iterate the jsonArray and print the info of JSONObjects
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //Get all data of above JSON data regarding category List
                    allCategoryDetails[0][i] = jsonObject.optString("sno");
                    allCategoryDetails[1][i] = jsonObject.optString("CategoryId").toString();
                    allCategoryDetails[2][i] = jsonObject.optString("CategoryName").toString();
                    allCategoryDetails[3][i] = jsonObject.optString("CategoryDescription").toString();
                    allCategoryDetails[4][i] = jsonObject.optString("ParentCategoryId").toString();
                    allCategoryDetails[5][i] = jsonObject.optString("ParentCategoryName").toString();
                    allCategoryDetails[6][i] = jsonObject.optString("Published").toString();
                    allCategoryDetails[7][i] = jsonObject.optString("DisplayOrder").toString();
                    allCategoryDetails[8][i] = jsonObject.optString("CreatedDate").toString();
                    allCategoryDetails[9][i] = jsonObject.optString("ShowOnHomePage").toString();
                    allCategoryDetails[10][i] = jsonObject.optString("Images").toString();
                    allCategoryDetails[11][i] = jsonObject.optString("Metakeywords").toString();
                    allCategoryDetails[12][i] = jsonObject.optString("MetaDescription").toString();
                    allCategoryDetails[13][i] = jsonObject.optString("MetaTitle").toString();
                    allCategoryDetails[14][i] = jsonObject.optString("MetaTagUrlO").toString();
                    allCategoryDetails[15][i] = jsonObject.optString("MetaTagUrl").toString();
                    allCategoryDetails[16][i] = jsonObject.optString("URL").toString();
                    allCategoryDetails[17][i] = jsonObject.optString("LastModifiedBy").toString();
                    allCategoryDetails[18][i] = jsonObject.optString("LastModifiedDate").toString();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                if(e instanceof SocketTimeoutException)
                {
                    JSOUPEXCEPTIONS=3;      //Time out error
                }
            }

            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return(null);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            setListData(allCategoryDetails);
            ListView listViewTab1 = (ListView) findViewById(R.id.listViewTab1);
            ListView listViewTab2 = (ListView) findViewById(R.id.listViewTab2);
            Resources res =getResources();

            /**************** Create Custom Adapter *********/
            CustomAdapter adapter;
            adapter=new CustomAdapter(CustomListView,allCategoryDetailsArr,res );
            listViewTab1.setAdapter( adapter );
        }
    }

    public void onItemClick(int mPosition)
    {
        ListModel tempValues = ( ListModel) allCategoryDetailsArr.get(mPosition);
        Toast.makeText(context,"Pressed Item No : "+mPosition,Toast.LENGTH_SHORT).show();
    }

    public void setListData(String inString[][])
    {

            for (int i = 0; i < inString.length; i++) {
                final ListModel sched = new ListModel();
                /******* Firstly take data in model object ******/
                String output = inString[2][i].substring(0, 1).toUpperCase() + inString[2][i].substring(1);
                sched.setcategoryText(output);
                sched.setimgIcon("android_guitar");
                sched.setmetaText(inString[11][i]);
                /******** Take Model Object in ArrayList **********/
                allCategoryDetailsArr.add(sched);
            }
    }
}
