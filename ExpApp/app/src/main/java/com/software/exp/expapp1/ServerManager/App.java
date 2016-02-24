package com.software.exp.expapp1.ServerManager;

import android.content.Context;

/**
 * Created by work on 16/02/2016.
 */
public class App {
    public static ObjectGraph provide(Context context) {
        return ((Provider) context.getApplicationContext()).getObjectGraph();
    }

    public interface Provider<T extends ObjectGraph> {
        T getObjectGraph();
    }
}
