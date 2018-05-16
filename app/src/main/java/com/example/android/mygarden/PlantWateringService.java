package com.example.android.mygarden;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.android.mygarden.provider.PlantContract;
import com.example.android.mygarden.utils.PlantUtils;

import static com.example.android.mygarden.provider.PlantContract.BASE_CONTENT_URI;
import static com.example.android.mygarden.provider.PlantContract.PATH_PLANTS;

public class PlantWateringService extends IntentService {
    public static final String ACTION_WATER_PLANTS =
            "com.example.android.mygarden.action.water_plants";

    public PlantWateringService() {
        super(PlantWateringService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            switch (intent.getAction()) {
                case ACTION_WATER_PLANTS:
                    handleActionWaterPlants();
                    break;
            }
        }
    }

    public static void startActionWaterPlants(Context context) {
        Intent intent = new Intent(context, PlantWateringService.class);
        intent.setAction(ACTION_WATER_PLANTS);
        context.startService(intent);
    }

    private void handleActionWaterPlants() {
        Uri plantsUri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLANTS).build();
        ContentValues values = new ContentValues();

        long now = System.currentTimeMillis();
        values.put(PlantContract.PlantEntry.COLUMN_LAST_WATERED_TIME, now);

        getContentResolver().update(plantsUri, values, PlantContract.PlantEntry.COLUMN_LAST_WATERED_TIME + ">?",
                new String[] { String.valueOf(now - PlantUtils.MAX_AGE_WITHOUT_WATER) });
    }
}
