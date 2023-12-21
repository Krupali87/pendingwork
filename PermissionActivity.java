package com.app.gpsphonelocator_new.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.gpsphonelocator_new.R;

import com.app.gpsphonelocator_new.databinding.ActivityPermissionBinding;
import com.app.gpsphonelocator_new.extensions.AppExtensionKt;
import com.app.gpsphonelocator_new.phone.service.GPSLocationService;
import com.app.gpsphonelocator_new.phone.util.AppAuthen;
import com.app.gpsphonelocator_new.phone.util.Constants;

import java.util.Objects;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;


public final class PermissionActivity extends BaseActivity<ActivityPermissionBinding> {
    private AlertDialog alert;
    private AlertDialog.Builder builder;

    public static  ActivityPermissionBinding access$getMBinding(PermissionActivity permissionActivity) {
        return (ActivityPermissionBinding) permissionActivity.getMBinding();
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        bindComponent();
        if (areAllPermissionsGranted()) {
            startHomeOptionActivity();
            finish();
        } else {
            // Permissions not granted, continue with normal initialization
            bindEvent();
            setEnableGo();
        }
    }

    private boolean areAllPermissionsGranted() {
        return (Build.VERSION.SDK_INT >= 23 &&
                ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 &&
                ActivityCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0) ||
                (Build.VERSION.SDK_INT > 32 &&
                        ActivityCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") == 0 &&
                        ActivityCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") == 0) ||
                (Build.VERSION.SDK_INT <= 32 &&
                        ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0);
    }

    private void startHomeOptionActivity() {
        Intent intent = new Intent(PermissionActivity.this, HomeOptionActivity.class);
        startActivity(intent);
        AppExtensionKt.setPref(getApplicationContext(), Constants.PERMISSION, true);
        finishAffinity();
    }


    public ActivityPermissionBinding getViewBinding() {
        ActivityPermissionBinding inflate = ActivityPermissionBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(layoutInflater)");
        return inflate;
    }

    private  void bindComponent() {
        setCheckedSwitchPermission();
        showGoToSetting();

    }

    private  void setCheckedSwitchPermission() {
        boolean z = true;
        if (Build.VERSION.SDK_INT >= 23) {
            Context context = this;
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchPreciseLocation.setChecked(ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0);
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchCamera.setChecked(ActivityCompat.checkSelfPermission(context, "android.permission.CAMERA") == 0);
        }
        if (Build.VERSION.SDK_INT > 32) {
            CardView cardView = ((ActivityPermissionBinding) getMBinding()).cvNotification;
            Intrinsics.checkNotNullExpressionValue(cardView, "mBinding.cvNotification");
            AppExtensionKt.show(cardView);
            Context context2 = this;
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchNotification.setChecked(ActivityCompat.checkSelfPermission(context2, "android.permission.POST_NOTIFICATIONS") == 0);
            SwitchCompat switchCompat = ((ActivityPermissionBinding) getMBinding()).onOffSwitchStorage;
            if (ActivityCompat.checkSelfPermission(context2, "android.permission.READ_MEDIA_IMAGES") != 0) {
                z = false;
            }
            switchCompat.setChecked(z);
            return;
        }
        SwitchCompat switchCompat2 = ((ActivityPermissionBinding) getMBinding()).onOffSwitchStorage;
        if (ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            z = false;
        }
        switchCompat2.setChecked(z);
        CardView cardView2 = ((ActivityPermissionBinding) getMBinding()).cvNotification;
        Intrinsics.checkNotNullExpressionValue(cardView2, "mBinding.cvNotification");
        AppExtensionKt.hide(cardView2);
    }

    private  void setEnableGo() {
        if (Build.VERSION.SDK_INT > 32) {
            Context context = this;
            if (ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(context, "android.permission.CAMERA") == 0 && ActivityCompat.checkSelfPermission(context, "android.permission.READ_MEDIA_IMAGES") == 0 && ActivityCompat.checkSelfPermission(context, "android.permission.POST_NOTIFICATIONS") == 0) {
                TextView textView = ((ActivityPermissionBinding) getMBinding()).tvGo;
                Intrinsics.checkNotNullExpressionValue(textView, "mBinding.tvGo");
                AppExtensionKt.setColorText(textView, R.color.color_32BE4B);
                ((ActivityPermissionBinding) getMBinding()).tvGo.setEnabled(true);
                return;
            }
            TextView textView2 = ((ActivityPermissionBinding) getMBinding()).tvGo;
            Intrinsics.checkNotNullExpressionValue(textView2, "mBinding.tvGo");
            AppExtensionKt.setColorText(textView2, R.color.color_96A3AB);
            ((ActivityPermissionBinding) getMBinding()).tvGo.setEnabled(false);
            return;
        }
        Context context2 = this;
        if (ActivityCompat.checkSelfPermission(context2, "android.permission.ACCESS_FINE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(context2, "android.permission.CAMERA") == 0 && ActivityCompat.checkSelfPermission(context2, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            TextView textView3 = ((ActivityPermissionBinding) getMBinding()).tvGo;
            Intrinsics.checkNotNullExpressionValue(textView3, "mBinding.tvGo");
            AppExtensionKt.setColorText(textView3, R.color.color_32BE4B);
            ((ActivityPermissionBinding) getMBinding()).tvGo.setEnabled(true);
            return;
        }
        TextView textView4 = ((ActivityPermissionBinding) getMBinding()).tvGo;
        Intrinsics.checkNotNullExpressionValue(textView4, "mBinding.tvGo");
        AppExtensionKt.setColorText(textView4, R.color.color_96A3AB);
        ((ActivityPermissionBinding) getMBinding()).tvGo.setEnabled(false);
    }

    private void bindEvent() {
        ((ActivityPermissionBinding) Objects.requireNonNull(getMBinding())).tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intrinsics.checkNotNullParameter(this, "this$0");

                    Intent intent = new Intent(PermissionActivity.this, HomeOptionActivity.class);
                    startActivity(intent);
                    AppExtensionKt.setPref(getApplicationContext(), Constants.PERMISSION, true);
                    finishAffinity();


            }
        });
        ((ActivityPermissionBinding) getMBinding()).viewOnOffSwitchPreciseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intrinsics.checkNotNullParameter(this, "this$0");
                requestPermissionLocation();
            }
        });
        ((ActivityPermissionBinding) getMBinding()).viewOnOffSwitchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intrinsics.checkNotNullParameter(this, "this$0");
                requestPermissionCamera();
            }
        });
        ((ActivityPermissionBinding) getMBinding()).viewOnOffSwitchNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intrinsics.checkNotNullParameter(this, "this$0");
                requestPermissionNotification();
            }
        });
        ((ActivityPermissionBinding) getMBinding()).viewOnOffSwitchStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intrinsics.checkNotNullParameter(this, "this$0");
               requestPermissionStorage();
            }
        });
    }


    private final void requestPermissionLocation() {
        if (Build.VERSION.SDK_INT < 24) {
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchPreciseLocation.setChecked(true);
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1111);
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchPreciseLocation.setChecked(false);
        } else {
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchPreciseLocation.setChecked(true);

        }
    }

    private final void requestPermissionCamera() {
        if (Build.VERSION.SDK_INT < 23) {
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchCamera.setChecked(true);

        } else if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 1222);
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchCamera.setChecked(false);

        } else {
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchCamera.setChecked(true);

        }
    }

    private final void requestPermissionStorage() {
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") != 0) {
                requestPermissions(new String[]{"android.permission.READ_MEDIA_IMAGES"}, 1333);
                ((ActivityPermissionBinding) getMBinding()).onOffSwitchStorage.setChecked(false);

                return;
            }
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchStorage.setChecked(true);

        } else if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1333);
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchStorage.setChecked(false);

        } else {
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchStorage.setChecked(true);

        }
    }

    private final void requestPermissionNotification() {
        if (Build.VERSION.SDK_INT < 32) {
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchNotification.setChecked(true);

        } else if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != 0) {
            requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 1444);
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchNotification.setChecked(false);

        } else {
            ((ActivityPermissionBinding) getMBinding()).onOffSwitchNotification.setChecked(true);

        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        Intrinsics.checkNotNullParameter(strArr, "permissions");
        Intrinsics.checkNotNullParameter(iArr, "grantResults");
        super.onRequestPermissionsResult(i, strArr, iArr);
        AlertDialog alertDialog = null;
        if (i == 1111) {
            if (!(iArr.length == 0)) {
                if (iArr[0] == 0) {
                    ((ActivityPermissionBinding) getMBinding()).onOffSwitchPreciseLocation.setChecked(true);

                } else if (Build.VERSION.SDK_INT >= 23) {
                    if (!shouldShowRequestPermissionRationale(strArr[0])) {
                        AlertDialog alertDialog2 = this.alert;
                        if (alertDialog2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("alert");
                        } else {
                            alertDialog = alertDialog2;
                        }
                        alertDialog.show();

                    }
                    ((ActivityPermissionBinding) getMBinding()).onOffSwitchPreciseLocation.setChecked(false);
                }
            }
            setEnableGo();
        } else if (i == 1222) {
            if ((!(iArr.length == 0)) && Build.VERSION.SDK_INT >= 23) {
                if (iArr[0] == 0) {
                    ((ActivityPermissionBinding) getMBinding()).onOffSwitchCamera.setChecked(true);

                } else if (!shouldShowRequestPermissionRationale(strArr[0])) {
                    AlertDialog alertDialog3 = this.alert;
                    if (alertDialog3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("alert");
                    } else {
                        alertDialog = alertDialog3;
                    }
                    alertDialog.show();

                } else {
                    ((ActivityPermissionBinding) getMBinding()).onOffSwitchCamera.setChecked(false);
                }
            }
            setEnableGo();
        } else if (i == 1333) {
            if (!(iArr.length == 0)) {
                if (Build.VERSION.SDK_INT > 32) {
                    if (iArr[0] == 0) {
                        ((ActivityPermissionBinding) getMBinding()).onOffSwitchStorage.setChecked(true);

                    } else if (!shouldShowRequestPermissionRationale(strArr[0])) {
                        AlertDialog alertDialog4 = this.alert;
                        if (alertDialog4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("alert");
                        } else {
                            alertDialog = alertDialog4;
                        }
                        alertDialog.show();

                    } else {
                        ((ActivityPermissionBinding) getMBinding()).onOffSwitchStorage.setChecked(false);
                    }
                } else if (iArr[0] == 0) {
                    ((ActivityPermissionBinding) getMBinding()).onOffSwitchStorage.setChecked(true);

                } else if (!shouldShowRequestPermissionRationale(strArr[0])) {
                    AlertDialog alertDialog5 = this.alert;
                    if (alertDialog5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("alert");
                    } else {
                        alertDialog = alertDialog5;
                    }
                    alertDialog.show();

                } else {
                    ((ActivityPermissionBinding) getMBinding()).onOffSwitchStorage.setChecked(false);
                }
            }
            setEnableGo();
        } else if (i == 1444) {
            if ((!(iArr.length == 0)) && Build.VERSION.SDK_INT >= 23) {
                if (iArr[0] == 0) {
                    ((ActivityPermissionBinding) getMBinding()).onOffSwitchNotification.setChecked(true);

                } else if (!shouldShowRequestPermissionRationale(strArr[0])) {
                    AlertDialog alertDialog6 = this.alert;
                    if (alertDialog6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("alert");
                    } else {
                        alertDialog = alertDialog6;
                    }
                    alertDialog.show();

                } else {
                    ((ActivityPermissionBinding) getMBinding()).onOffSwitchNotification.setChecked(false);
                }
            }
            setEnableGo();
        }
    }

    private final void showGoToSetting() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        this.builder = builder2;
        builder2.setTitle((int) R.string.grant_permission);
        AlertDialog.Builder builder3 = this.builder;
        AlertDialog.Builder builder4 = null;
        if (builder3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
            builder3 = null;
        }
        builder3.setMessage((int) R.string.please_grant_all_permissions);
        AlertDialog.Builder builder5 = this.builder;
        if (builder5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
            builder5 = null;
        }
        builder5.setCancelable(false);
        AlertDialog.Builder builder6 = this.builder;
        if (builder6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
            builder6 = null;
        }
        builder6.setPositiveButton((int) R.string.go_to_setting, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intrinsics.checkNotNullParameter(this, "this$0");
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", PermissionActivity.this.getPackageName(), (String) null));
                startActivity(intent);

            }
        });
        AlertDialog.Builder builder7 = this.builder;
        if (builder7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("builder");
        } else {
            builder4 = builder7;
        }
        AlertDialog create = builder4.create();
        Intrinsics.checkNotNullExpressionValue(create, "builder.create()");
        this.alert = create;
    }


    public static final void showGoToSetting$lambda$5(PermissionActivity permissionActivity, DialogInterface dialogInterface, int i) {

    }


    public void onResume() {
        super.onResume();
        setCheckedSwitchPermission();
        setEnableGo();

        AlertDialog alertDialog = this.alert;
        if (alertDialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alert");
            alertDialog = null;
        }
        if (alertDialog.isShowing()) {

            return;
        }

    }

    private  void startLocationService() {
        Context context = this;
        boolean z = ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0;
        boolean loginUser = AppAuthen.getInstance().loginUser(context);
        if (z && loginUser) {
            if (Build.VERSION.SDK_INT >= 26) {
                startForegroundService(new Intent(context, GPSLocationService.class));
            } else {
                startService(new Intent(context, GPSLocationService.class));
            }
        }
    }


}
