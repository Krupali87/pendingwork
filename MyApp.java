package com.app.gpsphonelocator_new;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class MyApp {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);

    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean getNormalMap(Context context) {
            Intrinsics.checkNotNullParameter(context, "mContext");
            return context.getSharedPreferences(context.getPackageName(), Context.MODE_MULTI_PROCESS).getBoolean("KEY_NORMAL_MAP", true);
        }

        public final void setNormalMap(Context context, boolean z) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.getSharedPreferences(context.getPackageName(), Context.MODE_MULTI_PROCESS).edit().putBoolean("KEY_NORMAL_MAP", z).apply();
        }

        @SuppressLint("WrongConstant")
        public final boolean getHybridMap(Context context) {
            Intrinsics.checkNotNullParameter(context, "mContext");
            return context.getSharedPreferences(context.getPackageName(), 4).getBoolean("KEY_HYBRID_MAP", false);
        }

        @SuppressLint("WrongConstant")
        public final void setHybridMap(Context context, boolean z) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.getSharedPreferences(context.getPackageName(), 4).edit().putBoolean("KEY_HYBRID_MAP", z).apply();
        }

        @SuppressLint("WrongConstant")
        public final boolean getSatelliteMap(Context context) {
            Intrinsics.checkNotNullParameter(context, "mContext");
            return context.getSharedPreferences(context.getPackageName(), 4).getBoolean("KEY_SATELLITE_MAP", false);
        }

        @SuppressLint("WrongConstant")
        public final void setSatelliteMap(Context context, boolean z) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.getSharedPreferences(context.getPackageName(), 4).edit().putBoolean("KEY_SATELLITE_MAP", z).apply();
        }

        @SuppressLint("WrongConstant")
        public final boolean getTerrainMap(Context context) {
            Intrinsics.checkNotNullParameter(context, "mContext");
            return context.getSharedPreferences(context.getPackageName(), 4).getBoolean("KEY_TERRAIN_MAP", false);
        }

        @SuppressLint("WrongConstant")
        public final void setTerrainMap(Context context, boolean z) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.getSharedPreferences(context.getPackageName(), 4).edit().putBoolean("KEY_TERRAIN_MAP", z).apply();
        }

        public final int getCountOpenApp(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return context.getSharedPreferences("data", 0).getInt("counts", 0);
        }

        public final void increaseCountOpenApp(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            SharedPreferences sharedPreferences = context.getSharedPreferences("data", 0);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt("counts", sharedPreferences.getInt("counts", 0) + 1);
            edit.commit();
        }
    }
}
