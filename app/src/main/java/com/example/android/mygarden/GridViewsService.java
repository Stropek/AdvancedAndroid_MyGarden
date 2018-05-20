package com.example.android.mygarden;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class GridViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}
