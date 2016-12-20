package de.bk_alsdorf.pcaapp.views;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.bk_alsdorf.pcaapp.Data;
import de.bk_alsdorf.pcaapp.MainActivity;
import de.bk_alsdorf.pcaapp.R;

import static de.bk_alsdorf.pcaapp.Data.setBolusAmount;
import static de.bk_alsdorf.pcaapp.R.id.bolusAmountInput;

public class ResultFragment extends Fragment {

    private TextView resultDate;
    private TextView basalRateResult;
    private TextView cartridgeResult;
    private TextView durationResult;
    private TextView ingredientQuantityResult;
    private TextView dosageResult;
    private TextView bolusAmountResult;
    private TextView bolusLockResult;
    private TextView boliPerHourResult;
    private Button screenshotButton;

    public ResultFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initializeResultView(inflater, container);
    }

    @Override
    public void setUserVisibleHint (boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) updateResults();
    }

    public void updateResults() {
        String basalRate = Data.getBasalRate() + " mg/h";
        basalRateResult.setText(basalRate);

        if (Data.getIngredientQuantity().length() > 0 && !Data.getIngredientQuantity().equals("0.0")) {
            String ingredientQuantity = Data.getIngredientQuantity() + " mg";
            ingredientQuantityResult.setText(ingredientQuantity);
        }

        String cartridge = Data.getCartridge() + " ml";
        cartridgeResult.setText(cartridge);

        String duration = Data.getDuration() + " Tage";
        durationResult.setText(duration);

        dosageResult.setText(Data.getDosage());

        if(Data.getBolusUnit().equals("mg")) {
            bolusAmountResult.setText(Data.getBasalRate() + " " + Data.getBolusUnit());
        } else {
            if (Data.getBolusAmount().length() > 0 && !Data.getBolusAmount().equals("0.0")) {
                String bolusAmount = Data.getBolusAmount() + " " + Data.getBolusUnit();
                bolusAmountResult.setText(bolusAmount);
            }
        }

        if (Data.getBolusLock().length() > 0 && !Data.getBolusLock().equals("0.0")) {
            String bolusLock = Data.getBolusLock() + " Minuten";
            bolusLockResult.setText(bolusLock);
        }

        if (Data.getBoliPerHour().length() > 0 && !Data.getBoliPerHour().equals("0.0")) {
            String boliPerHour = Data.getBoliPerHour() + " pro Stunde";
            boliPerHourResult.setText(boliPerHour);
        }
    }

    private View initializeResultView(LayoutInflater inflater, ViewGroup container) {
        final View resultView = inflater.inflate(R.layout.activity_result, container, false);

        resultDate = (TextView) resultView.findViewById(R.id.resultDate);
        basalRateResult = (TextView) resultView.findViewById(R.id.basalRateResult);
        cartridgeResult = (TextView) resultView.findViewById(R.id.cartridgeResult);
        durationResult = (TextView) resultView.findViewById(R.id.durationResult);
        ingredientQuantityResult = (TextView) resultView.findViewById(R.id.ingredientQuantityResult);
        dosageResult = (TextView) resultView.findViewById(R.id.dosageResult);
        bolusAmountResult = (TextView) resultView.findViewById(R.id.bolusAmountResult);
        bolusLockResult = (TextView) resultView.findViewById(R.id.bolusLockResult);
        boliPerHourResult = (TextView) resultView.findViewById(R.id.boliPerHourResult);
        screenshotButton = (Button) resultView.findViewById(R.id.screenshotBtn);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String date = df.format(Calendar.getInstance().getTime());

        resultDate.setText(date);

        screenshotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeScreenshot(getScreenShot(v));
            }
        });

        return resultView;
    }

    private Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private void storeScreenshot(Bitmap bitmap){
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots" + now + ".jpg";
        File imageFile = new File(dirPath);
        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        shareImage(imageFile);
    }
    private void shareImage(File file){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
