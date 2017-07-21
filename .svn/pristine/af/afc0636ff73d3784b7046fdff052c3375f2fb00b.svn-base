package com.zenchn.electrombile.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenchn.electrombile.R;

/**
 * Created by wangr on 2016/11/1 0001.
 */
public class CommonDialog extends Dialog {

    public CommonDialog(Context context) {
        super(context);
    }

    public CommonDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {

        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private boolean cancelable;
        private boolean canceledOnTouchOutside;
        private boolean hideTitle;
        private boolean hidePositiveButton;
        private boolean hideNegativeButton;
        private int resource;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public void setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
        }

        public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
        }

        public void isHidePositiveButton(boolean hidePositiveButton) {
            this.hidePositiveButton = hidePositiveButton;
        }

        public void isHideNegativeButton(boolean hideNegativeButton) {
            this.hideNegativeButton = hideNegativeButton;
        }

        public void setHideTitle(boolean hideTitle) {
            this.hideTitle = hideTitle;
        }

        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setLayout(int resource) {
            this.resource = resource;
            return this;
        }

        public void setPositiveButtonText(String positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
        }

        public void setNegativeButtonText(String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
        }

        public CommonDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final CommonDialog dialog = new CommonDialog(context, R.style.AppTheme_Dialog);

            View layout = inflater.inflate(R.layout.dialog_common_layout, null);

            //是否隐藏标题
            if (hideTitle || title == null || TextUtils.isEmpty(title)) {
                ((TextView) layout.findViewById(R.id.title)).setVisibility(View.GONE);
            } else {
                ((TextView) layout.findViewById(R.id.title)).setText(title);
            }

            //是否隐藏积极按钮
            if (hidePositiveButton) {
                layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
            } else {
                if (positiveButtonClickListener != null) {
                    if (positiveButtonText != null)
                        ((TextView) layout.findViewById(R.id.tv_positiveButton)).setText(positiveButtonText);
                    ((LinearLayout) layout.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            }

            //是否隐藏消极按钮
            if (hideNegativeButton) {
                layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
                layout.findViewById(R.id.separate).setVisibility(View.GONE);
            } else {
                if (negativeButtonText != null)
                    ((TextView) layout.findViewById(R.id.tv_negativeButton)).setText(negativeButtonText);
                ((LinearLayout) layout.findViewById(R.id.negativeButton)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            }

            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            }

            if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            dialog.setCancelable(cancelable);

            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

            dialog.setCancelable(cancelable);

            return dialog;
        }

    }
}
