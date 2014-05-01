package org.floens.chan.utils;

import org.floens.chan.ChanApplication;
import org.floens.chan.R;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Utils {
    private static DisplayMetrics displayMetrics;

    public final static ViewGroup.LayoutParams MATCH_PARAMS = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    public final static ViewGroup.LayoutParams WRAP_PARAMS = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public final static ViewGroup.LayoutParams MATCH_WRAP_PARAMS = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public final static ViewGroup.LayoutParams WRAP_MATCH_PARAMS = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

    public static int dp(float dp) {
        //        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        if (displayMetrics == null) {
            displayMetrics = ChanApplication.getInstance().getResources().getDisplayMetrics();
        }

        return (int) (dp * displayMetrics.density);
    }

    /**
     * Sets the android.R.attr.selectableItemBackground as background drawable
     * on the view.
     * 
     * @param view
     */
    @SuppressWarnings("deprecation")
    public static void setPressedDrawable(View view) {
        Drawable drawable = Utils.getSelectableBackgroundDrawable(view.getContext());
        view.setBackgroundDrawable(drawable);
    }

    public static Drawable getSelectableBackgroundDrawable(Context context) {
        TypedArray arr = context.obtainStyledAttributes(new int[] { android.R.attr.selectableItemBackground });

        Drawable drawable = arr.getDrawable(0);

        arr.recycle();

        return drawable;
    }

    /**
     * Causes the runnable to be added to the message queue. The runnable will
     * be run on the ui thread.
     * 
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
    
    public static void requestKeyboardFocus(Dialog dialog, final View view) {
        view.requestFocus();
        dialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, 0);
            }
        });
    }
    
    public static void openLink(Context context, String link) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, R.string.open_link_failed, Toast.LENGTH_LONG).show();
        }
    }
}
