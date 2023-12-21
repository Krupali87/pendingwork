package com.app.gpsphonelocator_new.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.gpsphonelocator_new.R;
import com.app.gpsphonelocator_new.adapter.NotificationAdapter;
import com.app.gpsphonelocator_new.database.AlertHistoryDao;
import com.app.gpsphonelocator_new.database.AlertHistoryEntity;
import com.app.gpsphonelocator_new.database.RTDB_DEFINE;
import com.app.gpsphonelocator_new.database.UserDatabase;
import com.app.gpsphonelocator_new.databinding.ActivityHomeOptionBinding;
import com.app.gpsphonelocator_new.databinding.LayoutDialogDeleteAllNotificationBinding;
import com.app.gpsphonelocator_new.extensions.AppExtensionKt;
import com.app.gpsphonelocator_new.phone.util.AppAuthen;
import com.app.gpsphonelocator_new.phone.util.AppUserSingleton;
import com.app.gpsphonelocator_new.phone.util.Constants;
import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;


public final class HomeOptionActivity extends BaseActivity<ActivityHomeOptionBinding> {

    private ArrayList listNotification = new ArrayList();
    private final Lazy notificationAdapter$delegate = LazyKt.lazy(new Function0<Object>() {

        @Override
        public Object invoke() {
            return new NotificationAdapter();
        }
    });

    public static final ActivityHomeOptionBinding access$getMBinding(HomeOptionActivity homeOptionActivity) {
        return (ActivityHomeOptionBinding) homeOptionActivity.getMBinding();
    }

    private final NotificationAdapter getNotificationAdapter() {
        return (NotificationAdapter) this.notificationAdapter$delegate.getValue();
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AppAuthen.getInstance().appContext = getApplicationContext();
        AppUserSingleton.getInstance().appContext = getApplicationContext();
        bindComponent();
        bindEvent();


    }

    public ActivityHomeOptionBinding getViewBinding() {
        ActivityHomeOptionBinding inflate = ActivityHomeOptionBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(layoutInflater)");
        return inflate;

    }

    private final void bindComponent() {
        getSOS();

    }


    public void onResume() {
        super.onResume();
     setAvatar();
        setReadNotification();

    }

    private final void setReadNotification() {
        if (AppExtensionKt.getPref((Context) this, Constants.NOTIFICATION_SERVICE, false)) {
            ImageView imageView = ((ActivityHomeOptionBinding) Objects.requireNonNull(getMBinding())).ivNotificationEnable;
            Intrinsics.checkNotNullExpressionValue(imageView, "mBinding.ivNotificationEnable");
            AppExtensionKt.show(imageView);
            ImageView imageView2 = ((ActivityHomeOptionBinding) getMBinding()).ivNotificationDisable;
            Intrinsics.checkNotNullExpressionValue(imageView2, "mBinding.ivNotificationDisable");
            AppExtensionKt.hide(imageView2);
            return;
        }
        ImageView imageView3 = ((ActivityHomeOptionBinding) getMBinding()).ivNotificationEnable;
        Intrinsics.checkNotNullExpressionValue(imageView3, "mBinding.ivNotificationEnable");
        AppExtensionKt.hide(imageView3);
        ImageView imageView4 = ((ActivityHomeOptionBinding) getMBinding()).ivNotificationDisable;
        Intrinsics.checkNotNullExpressionValue(imageView4, "mBinding.ivNotificationDisable");
        AppExtensionKt.show(imageView4);
    }

    private final void getSOS() {
        AppAuthen.AuthUser currentUser = AppAuthen.getInstance().getCurrentUser();
        if (currentUser == null) {
            Context context = this;
            if (!AppAuthen.getInstance().loginUser(context)) {
              startActivity(new Intent(context, SetMailAndCodeActivity.class));
                finishAffinity();
            }
        }
       // DatabaseReference child = FirebaseDatabase.getInstance().getReference().child(RTDB_DEFINE.TABLE_USER);
        Intrinsics.checkNotNull(currentUser);
       // DatabaseReference child2 = child.child(currentUser.uid);
      //  Intrinsics.checkNotNullExpressionValue(child2, "getInstance().reference.…E_USER).child(user!!.uid)");
//        child2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Intrinsics.checkNotNullParameter(dataSnapshot, "snapshot");
//                if (Boolean.parseBoolean(String.valueOf(dataSnapshot.child("sos").getValue()))) {
//                    HomeOptionActivity.access$getMBinding(HomeOptionActivity.this).ivSos.setImageResource(R.drawable.ic_sos_on);
//                } else {
//                    HomeOptionActivity.access$getMBinding(HomeOptionActivity.this).ivSos.setImageResource(R.drawable.ic_sos_off);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Intrinsics.checkNotNullParameter(databaseError, "error");
//            }
//        });
    }

    private  void setAvatar() {
        Glide.with((FragmentActivity) this).load(AppUserSingleton.getInstance().getAvt()).into((ImageView) ((ActivityHomeOptionBinding) Objects.requireNonNull(getMBinding())).ivAvatar);
    }

    private final void bindEvent() {
        ((ActivityHomeOptionBinding) getMBinding()).cvRealTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intrinsics.checkNotNullParameter(this, "this$0");
                AppExtensionKt.showActivity$default(HomeOptionActivity.this, MainActivity.class, (Bundle) null, 4, (Object) null);
            }
        });
        ((ActivityHomeOptionBinding) getMBinding()).cvListUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intrinsics.checkNotNullParameter(this, "this$0");
                if (!isNetworkAvailable()) {
                    new HomeOptionActivity();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("message", Constants.from_home);
                AppExtensionKt.showActivity(HomeOptionActivity.this, TrackingUserListActivity.class, bundle);
            }
        });
        ((ActivityHomeOptionBinding) getMBinding()).cvMySharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intrinsics.checkNotNullParameter(this, "this$0");
                if (!isNetworkAvailable()) {
                    new HomeOptionActivity();
                    return;
                }
               startActivity(new Intent(HomeOptionActivity.this, ShareMyLocationActivity.class));
            }
        });
        ((ActivityHomeOptionBinding) getMBinding()).cvZoneAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intrinsics.checkNotNullParameter(this, "this$0");
                if (!isNetworkAvailable()) {
                    new HomeOptionActivity();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("message", Constants.zone_alert_from_home);
                AppExtensionKt.showActivity(HomeOptionActivity.this, ZoneAlertActivity.class, bundle);
            }
        });
        ((ActivityHomeOptionBinding) getMBinding()).cvSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intrinsics.checkNotNullParameter(this, "this$0");
                if (!isNetworkAvailable()) {
                    new HomeOptionActivity();
                    return;
                }
                AppExtensionKt.showActivity$default(HomeOptionActivity.this, SOSActivity.class, (Bundle) null, 4, (Object) null);
            }
        });
        ((ActivityHomeOptionBinding) getMBinding()).cvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intrinsics.checkNotNullParameter(this, "this$0");
                AppExtensionKt.showActivity$default(HomeOptionActivity.this, SettingActivity.class, (Bundle) null, 4, (Object) null);
            }
        });
        ((ActivityHomeOptionBinding) getMBinding()).ivNotificationEnable.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                Intrinsics.checkNotNullParameter(this, "this$0");
                ConstraintLayout constraintLayout = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).loPopUpNotification;
                Intrinsics.checkNotNullExpressionValue(constraintLayout, "mBinding.loPopUpNotification");
                if (constraintLayout.getVisibility() == 0) {
                    ConstraintLayout constraintLayout2 = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).loPopUpNotification;
                    Intrinsics.checkNotNullExpressionValue(constraintLayout2, "mBinding.loPopUpNotification");
                    AppExtensionKt.hide(constraintLayout2);
                    View view2 = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).outSideNotification;
                    Intrinsics.checkNotNullExpressionValue(view2, "mBinding.outSideNotification");
                    AppExtensionKt.hide(view2);
                } else {
                    ConstraintLayout constraintLayout3 = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).loPopUpNotification;
                    Intrinsics.checkNotNullExpressionValue(constraintLayout3, "mBinding.loPopUpNotification");
                    AppExtensionKt.show(constraintLayout3);
                    View view3 = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).outSideNotification;
                    Intrinsics.checkNotNullExpressionValue(view3, "mBinding.outSideNotification");
                    AppExtensionKt.show(view3);
                    getDataRecyclerViewNotification();
                }
                AppExtensionKt.setPref((Context) HomeOptionActivity.this, Constants.NOTIFICATION_SERVICE, false);
                setReadNotification();
            }
        });
        ((ActivityHomeOptionBinding) getMBinding()).ivNotificationDisable.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {

                Intrinsics.checkNotNullParameter(this, "this$0");
                ConstraintLayout constraintLayout = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).loPopUpNotification;
                Intrinsics.checkNotNullExpressionValue(constraintLayout, "mBinding.loPopUpNotification");
                if (constraintLayout.getVisibility() == 0) {
                    ConstraintLayout constraintLayout2 = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).loPopUpNotification;
                    Intrinsics.checkNotNullExpressionValue(constraintLayout2, "mBinding.loPopUpNotification");
                    AppExtensionKt.hide(constraintLayout2);
                    View view2 = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).outSideNotification;
                    Intrinsics.checkNotNullExpressionValue(view2, "mBinding.outSideNotification");
                    AppExtensionKt.hide(view2);
                } else {
                    ConstraintLayout constraintLayout3 = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).loPopUpNotification;
                    Intrinsics.checkNotNullExpressionValue(constraintLayout3, "mBinding.loPopUpNotification");
                    AppExtensionKt.show(constraintLayout3);
                    View view3 = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).outSideNotification;
                    Intrinsics.checkNotNullExpressionValue(view3, "mBinding.outSideNotification");
                    AppExtensionKt.show(view3);
                    getDataRecyclerViewNotification();
                }
                AppExtensionKt.setPref((Context) HomeOptionActivity.this, Constants.NOTIFICATION_SERVICE, false);
                setReadNotification();
            }
        });
        ((ActivityHomeOptionBinding) getMBinding()).outSideNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intrinsics.checkNotNullParameter(this, "this$0");
                View view2 = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).outSideNotification;
                Intrinsics.checkNotNullExpressionValue(view2, "mBinding.outSideNotification");
                AppExtensionKt.hide(view2);
                ConstraintLayout constraintLayout = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).loPopUpNotification;
                Intrinsics.checkNotNullExpressionValue(constraintLayout, "mBinding.loPopUpNotification");
                AppExtensionKt.hide(constraintLayout);
               setReadNotification();
            }
        });
        ((ActivityHomeOptionBinding) getMBinding()).tvClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intrinsics.checkNotNullParameter(this, "this$0");
                openPopUpDeleteAllNotification();
                View view2 = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).outSideNotification;
                Intrinsics.checkNotNullExpressionValue(view2, "mBinding.outSideNotification");
                AppExtensionKt.hide(view2);
                ConstraintLayout constraintLayout = ((ActivityHomeOptionBinding) HomeOptionActivity.this.getMBinding()).loPopUpNotification;
                Intrinsics.checkNotNullExpressionValue(constraintLayout, "mBinding.loPopUpNotification");
                AppExtensionKt.hide(constraintLayout);
               setReadNotification();
            }
        });
    }

    @SuppressLint("WrongConstant")
    private final void getDataRecyclerViewNotification() {
        List<AlertHistoryEntity> list;
        if (!this.listNotification.isEmpty()) {
            this.listNotification.clear();
        }
        List<AlertHistoryEntity> listAlertHistory = listAlertHistory();
        if (listAlertHistory == null || (list = CollectionsKt.toMutableList(listAlertHistory)) == null) {
            list = new ArrayList<>();
        }
        this.listNotification = (ArrayList) list;
        getNotificationAdapter().setData(this.listNotification);
        RecyclerView recyclerView = ((ActivityHomeOptionBinding) getMBinding()).recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
        recyclerView.setAdapter(getNotificationAdapter());
        if (this.listNotification.isEmpty()) {
            TextView textView = ((ActivityHomeOptionBinding) getMBinding()).tvEmpty;
            Intrinsics.checkNotNullExpressionValue(textView, "mBinding.tvEmpty");
            AppExtensionKt.show(textView);
            RecyclerView recyclerView2 = ((ActivityHomeOptionBinding) getMBinding()).recyclerView;
            Intrinsics.checkNotNullExpressionValue(recyclerView2, "mBinding.recyclerView");
            AppExtensionKt.hide(recyclerView2);
            TextView textView2 = ((ActivityHomeOptionBinding) getMBinding()).tvClearAll;
            Intrinsics.checkNotNullExpressionValue(textView2, "mBinding.tvClearAll");
            AppExtensionKt.hide(textView2);
            return;
        }
        TextView textView3 = ((ActivityHomeOptionBinding) getMBinding()).tvEmpty;
        Intrinsics.checkNotNullExpressionValue(textView3, "mBinding.tvEmpty");
        AppExtensionKt.hide(textView3);
        RecyclerView recyclerView3 = ((ActivityHomeOptionBinding) getMBinding()).recyclerView;
        Intrinsics.checkNotNullExpressionValue(recyclerView3, "mBinding.recyclerView");
        AppExtensionKt.show(recyclerView3);
        TextView textView4 = ((ActivityHomeOptionBinding) getMBinding()).tvClearAll;
        Intrinsics.checkNotNullExpressionValue(textView4, "mBinding.tvClearAll");
        AppExtensionKt.show(textView4);
    }

    private final List<AlertHistoryEntity> listAlertHistory() {
        AlertHistoryDao alertHistoryDAO;
        UserDatabase instance = UserDatabase.Companion.getInstance(this);
        if (instance == null || (alertHistoryDAO = instance.alertHistoryDAO()) == null) {
            return null;
        }
        return alertHistoryDAO.getHistoryList();
    }

    private final void deleteItemAlertHistory(int i) {
        AlertHistoryDao alertHistoryDAO;
        UserDatabase instance = UserDatabase.Companion.getInstance(this);
        if (instance != null && (alertHistoryDAO = instance.alertHistoryDAO()) != null) {
            alertHistoryDAO.delete(i);
        }
    }

    private final void deleteAllAlertHistory() {
        AlertHistoryDao alertHistoryDAO;
        UserDatabase instance = UserDatabase.Companion.getInstance(this);
        if (instance != null && (alertHistoryDAO = instance.alertHistoryDAO()) != null) {
            alertHistoryDAO.deleteAll();
        }
    }

    @SuppressLint("WrongConstant")
    private final void openPopUpDeleteAllNotification() {
        Context context = this;
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        LayoutDialogDeleteAllNotificationBinding inflate = LayoutDialogDeleteAllNotificationBinding.inflate(LayoutInflater.from(context));
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(LayoutInflater.f…this@HomeOptionActivity))");
        dialog.setContentView(inflate.getRoot());
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(-1, -2);
        }
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
        }
        WindowManager.LayoutParams attributes = window != null ? window.getAttributes() : null;
        if (attributes != null) {
            attributes.gravity = 17;
        }
        dialog.setCancelable(true);
        inflate.cvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intrinsics.checkNotNullParameter(dialog, "$dialog");
                dialog.dismiss();
            }
        });
        inflate.cvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeOptionActivity homeOptionActivity=null;
                Intrinsics.checkNotNullParameter(homeOptionActivity, "this$0");
                Intrinsics.checkNotNullParameter(dialog, "$dialog");
                homeOptionActivity.deleteAllAlertHistory();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onBackPressed() {
            super.onBackPressed();
        }
    }

