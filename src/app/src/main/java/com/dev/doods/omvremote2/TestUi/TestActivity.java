package com.dev.doods.omvremote2.TestUi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dev.doods.omvremote2.MyApplicationBase;
import com.dev.doods.omvremote2.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import Client.Response;
import Deserializers.SharesDeserializer;
import Models.Result;
import Models.SMBSettings;
import Models.SharedFolderNFS;
import Models.Shares;
import utils.Util;

import static com.dev.doods.omvremote2.MyApplicationBase.mAdRequest;

public class TestActivity extends AppCompatActivity {

    Button SharesDeserializerBtn ;
    Button SharedFolderNFSBtn ;
    Button LongParsingBtn;
    LinearLayout LinearLayoutTest;
    private CardView mCardViewAds;
    private NativeExpressAdView mBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        SharesDeserializerBtn = (Button) findViewById(R.id.SharesDeserializerBtn);

        SharesDeserializerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                testSharesDeserializer();

            }
        });

        SharedFolderNFSBtn = (Button) findViewById(R.id.SharedFolderNFSBtn);

        SharedFolderNFSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                testSharedFolderNFS();

            }
        });

        LongParsingBtn = (Button) findViewById(R.id.LongParsingBtn);

        LongParsingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                testLongParsing();

            }
        });


        LinearLayoutTest = (LinearLayout) findViewById(R.id.LinearLayoutTest);



        final AdView adView1 = (AdView) findViewById(R.id.banner1);
        adView1.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

            }
        });

        adView1.loadAd(MyApplicationBase.mAdRequest);
        final NativeExpressAdView adView2 = (NativeExpressAdView) findViewById(R.id.banner2);
        adView2.loadAd(MyApplicationBase.mAdRequest);



        mCardViewAds = (CardView)findViewById(R.id.card_view_ads);
        mBanner = (NativeExpressAdView)findViewById(R.id.banner);
        if(MyApplicationBase.light)
        {

            mBanner.loadAd(mAdRequest);
            mCardViewAds.setVisibility(View.VISIBLE);
        }

    }
    private String SharesDeserializerStr = "{\"response\":{\"enable\":false,\"workgroup\":\"WORKGROUP\",\"serverstring\":\"%h server\",\"loglevel\":0,\"usesendfile\":true,\"aio\":true,\"nullpasswords\":false,\"localmaster\":true,\"timeserver\":false,\"winssupport\":false,\"winsserver\":\"\",\"homesenable\":false,\"homesbrowseable\":true,\"extraoptions\":\"\",\"shares\":\"\"},\"error\":null}";
    private void testSharesDeserializer()
    {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Shares.class, new SharesDeserializer());
            //gsonBuilder.registerTypeAdapter(SystemInformation.class, new SystemInformationDeserializer());
            Gson gson = gsonBuilder.create();
            JsonElement jsonResponse = new JsonParser().parse(SharesDeserializerStr);
            Response response = new Response(jsonResponse);
            TypeToken tt = new TypeToken<SMBSettings>() {
            };
            Type t = tt.getType();

            JsonElement j = response.GetJsonResult();
            SMBSettings mSMBSettings = gson.fromJson(j, t);
        }
        catch (Exception ex)
        {
           String message = ex.getMessage();
        }

    }

    private String SharedFolderNFSStr = "{\"response\":{\"total\":0,\"data\":[]},\"error\":null}";
    private void testSharedFolderNFS()
    {
        try {
            JsonElement jsonResponse = new JsonParser().parse(SharedFolderNFSStr);
            Response response = new Response(jsonResponse);
            final Result<SharedFolderNFS> res = response.GetResultObject(new TypeToken<Result<SharedFolderNFS>>(){});
           List<SharedFolderNFS> lst = res.getData();
            if(lst ==null)
            {

            }
        }
        catch (Exception ex)
        {
            String message = ex.getMessage();
        }

    }


    private void testLongParsing()
    {
        try {
            Double sizeLong = Double.parseDouble("1929127510671.4");
            String sizeStr = Util.humanReadableByteCount(sizeLong,false);

            sizeLong++;

        }
        catch (Exception ex)
        {
            String message = ex.getMessage();
        }

        try {
            Double sizeLong = Double.parseDouble("19.4");
            String sizeStr = Util.humanReadableByteCount(sizeLong,false);
            sizeLong++;
        }
        catch (Exception ex)
        {
            String message = ex.getMessage();
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();


    }
    public void onPostResume()
    {
        super.onPostResume();
        LinearLayoutTest.addView(MyApplicationBase.mAdViewSmall);

    }
    @Override
    public void onPause()
    {
        super.onPause();
        LinearLayoutTest.removeView(MyApplicationBase.mAdViewSmall);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        //MyApplication.mAdViewSmall.removeView(LinearLayoutTest);
    }
}
