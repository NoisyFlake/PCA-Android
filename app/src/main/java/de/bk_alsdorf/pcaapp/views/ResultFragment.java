package de.bk_alsdorf.pcaapp.views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.bk_alsdorf.pcaapp.Data;
import de.bk_alsdorf.pcaapp.MainActivity;
import de.bk_alsdorf.pcaapp.R;

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

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
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

    private void updateResults() {
        String noValue = "Keine Angabe";

        String basalRate = (Data.getBasalRate() > 0) ? (Data.getBasalRate() + " mg/h") : noValue;
        basalRateResult.setText(basalRate);

        String ingredientQuantity = (Data.getIngredientQuantity() > 0) ? (Data.getIngredientQuantity() + " mg") : noValue;
        ingredientQuantityResult.setText(ingredientQuantity);

        String cartridge = Data.getCartridge() + " ml";
        cartridgeResult.setText(cartridge);

        String duration = Data.getDuration() + " Tage";
        durationResult.setText(duration);

        String bolusAmount = (Data.getBolusAmount() > 0) ? (Data.getBolusAmount() + " mg") : noValue;
        bolusAmountResult.setText(bolusAmount);

        String bolusLock = (Data.getBolusLock() > 0) ? (Data.getBolusLock() + " Minuten") : noValue;
        bolusLockResult.setText(bolusLock);

        String boliPerHour = (Data.getBoliPerHour() > 0) ? String.valueOf(Data.getBoliPerHour()) : noValue;
        boliPerHourResult.setText(boliPerHour);

        String dosage = String.valueOf(Data.getDosage());
        dosageResult.setText(dosage);
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
        final File dirPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/DCIM/Screenshots/");
        dirPath.mkdirs();
        File imageFile = new File(dirPath, now + ".jpg");
        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            MainActivity.addImageToGallery(imageFile.getAbsolutePath());
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
