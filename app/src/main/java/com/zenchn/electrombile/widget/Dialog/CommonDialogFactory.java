package com.zenchn.electrombile.widget.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zbar.lib.CaptureActivity;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.entity.InsuranceActivateInfo;
import com.zenchn.electrombile.utils.CommonUtils;

/**
 * Created by wangr on 2016/11/2.
 */
public class CommonDialogFactory {

    /**
     * 创建一个标准的提示框
     *
     * @param context
     * @param title
     * @param msg
     * @param listener
     * @return
     */
    public static CommonDialog createStandardDialog(Context context, String title, String msg, final OnConfirmListener listener) {

        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.contentview_ios_simple_style, null);
        TextView message = (TextView) contentView.findViewById(R.id.message);
        message.setText(msg);
        builder.setContentView(contentView);
        builder.setTitle(CommonUtils.isEmpty(title) ? "温馨提示" : title);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null)
                            listener.onConfirm();
                        dialog.dismiss();
                    }
                }
        );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        return builder.create();
    }

    /**
     * 创建一个标准的提示框
     *
     * @param context
     * @param msg
     * @param listener
     * @return
     */
    public static CommonDialog createStandardDialog(Context context, String msg, final OnConfirmListener listener) {
        return createStandardDialog(context, null, msg, listener);
    }

    /**
     * 创建一个通常的提示框(单选项)
     *
     * @param context
     * @return
     */
    public static CommonDialog createSimpleDialog(Context context, String title, String msg) {

        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        builder.isHideNegativeButton(true);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.contentview_ios_simple_style, null);
        TextView message = (TextView) contentView.findViewById(R.id.message);
        message.setText(msg);
        builder.setContentView(contentView);
        builder.setTitle(CommonUtils.isEmpty(title) ? "温馨提示" : title);
        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        return builder.create();
    }

    /**
     * 创建一个通常的提示框(单选项)
     *
     * @param context
     * @return
     */
    public static CommonDialog createSimpleDialog(Context context, String msg) {
        return createSimpleDialog(context, null, msg);
    }

    /**
     * 创建一个通常的提示框(可以自动消失)
     *
     * @param context
     * @return
     */
    public static void createSimpleDialog(Context context, String msg, long autoDismissTime) {
        final CommonDialog commonDialog = createSimpleDialog(context, msg);
        commonDialog.show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (commonDialog != null)
                    commonDialog.dismiss();
            }
        }, autoDismissTime);
    }

    /**
     * 创建一个发送指令的提示框
     *
     * @param context
     * @param msg
     * @param isSuccess
     * @return
     */
    public static CommonDialog createSendCmdDialog(Context context, String msg, boolean isSuccess) {

        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        builder.isHideNegativeButton(true);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(isSuccess ? R.layout.contentview_ios_style_success : R.layout.contentview_ios_style_error, null);
        TextView message = (TextView) contentView.findViewById(R.id.message);
        message.setText(msg);
        builder.setContentView(contentView);
        builder.setPositiveButton("朕知道了", new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }

        );
        return builder.create();
    }

    /**
     * 创建一个发送指令的提示框(可以自动消失)
     *
     * @param context
     * @return
     */
    public static void createSendTcpCmdStatusDialog(Context context, String msg, boolean isSuccess, long autoDismissTime) {
        final CommonDialog sendCmdDialog = createSendCmdDialog(context, msg, isSuccess);
        sendCmdDialog.show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sendCmdDialog != null)
                    sendCmdDialog.dismiss();
            }
        }, autoDismissTime);
    }

    /**
     * 创建一个查询到指令结果的提示框
     *
     * @param context
     * @param msg
     * @param isSuccess
     * @return
     */
    public static CommonDialog createQueryCmdDialog(Context context, String msg, boolean isSuccess) {

        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        builder.isHideNegativeButton(true);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(isSuccess ? R.layout.contentview_ios_style_success : R.layout.contentview_ios_style_error, null);
        TextView message = (TextView) contentView.findViewById(R.id.message);
        message.setText(msg);
        builder.setContentView(contentView);
        builder.setPositiveButton("朕知道了", new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }

        );
        return builder.create();
    }

    /**
     * 创建一个发送指令的提示框(可以自动消失)
     *
     * @param context
     * @return
     */
    public static void createQueryCmdDialog(Context context, String msg, boolean isSuccess, long autoDismissTime) {
        final CommonDialog sendCmdDialog = createQueryCmdDialog(context, msg, isSuccess);
        sendCmdDialog.show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sendCmdDialog != null)
                    sendCmdDialog.dismiss();
            }
        }, autoDismissTime);
    }

    /**
     * 创建一个带呼叫功能的的提示框
     *
     * @param context
     * @return
     */
    public static CommonDialog createCallDialog(final Context context, String msg, final String phoneNumber) {

        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.contentview_ios_simple_style, null);
        TextView message = (TextView) contentView.findViewById(R.id.message);
        message.setText(msg);
        builder.setContentView(contentView);
        builder.setHideTitle(true);
        builder.setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                }
        );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        return builder.create();
    }

    /**
     * 创建一个提示绑定的提示框
     *
     * @param context
     * @param encryptMobilePhone
     * @param listener
     * @return
     */
    public static CommonDialog createAskBindDialog(final Context context, String encryptMobilePhone, final OnCommitListener listener) {

        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.contentview_ios_simple_style_ask_mobile, null);
        TextView message = (TextView) contentView.findViewById(R.id.message);
        message.setGravity(Gravity.CENTER_HORIZONTAL);
        final EditText etMobilePhone = (EditText) contentView.findViewById(R.id.et_mobile_phone);
        builder.setTitle("绑定验证");
        message.setText("当前设备已被" + encryptMobilePhone + "用户绑定");
        builder.setContentView(contentView);
        builder.setPositiveButton("去验证", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mobilePhone = etMobilePhone.getText().toString();
                        listener.onCommit(mobilePhone);
                        dialog.dismiss();
                    }
                }
        );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onCancel();
                        dialog.dismiss();
                    }
                }
        );
        return builder.create();
    }

    /**
     * 创建一个提示绑定的提示框
     *
     * @param context
     * @return
     */
    public static CommonDialog createBindDialog(final Context context) {

        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.contentview_ios_simple_style_multi, null);
        TextView message = (TextView) contentView.findViewById(R.id.message);
        message.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setTitle("绑定车辆");
        message.setText("当前账号没有绑定车辆" + "\n" + "请先绑定车辆");
        builder.setContentView(contentView);
        builder.setPositiveButton("去绑定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(context, CaptureActivity.class));
                        dialog.dismiss();
                    }
                }
        );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        return builder.create();
    }

    /**
     * 创建一个提示绑定的提示框(可以自动消失)
     *
     * @param context
     * @param autoDismissTime
     */
    public static void createBindDialog(Context context, long autoDismissTime) {
        final CommonDialog bindDialog = createBindDialog(context);
        bindDialog.show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bindDialog != null)
                    bindDialog.dismiss();
            }
        }, autoDismissTime);
    }

    /**
     * 创建一个用户确认的提示框
     *
     * @param context
     * @param listener
     */
    public static CommonDialog createActivateConfirmDialog(final Context context, final InsuranceActivateInfo insuranceActivateInfo, final OnCommitListener<InsuranceActivateInfo> listener) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setCancelable(false);
        builder.setCanceledOnTouchOutside(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout contentView = (LinearLayout) inflater.inflate(R.layout.contentview_ios_simple_style_activate_confirm, null);
        ((TextView) contentView.findViewById(R.id.tv_name)).setText(insuranceActivateInfo.getName());
        ((TextView) contentView.findViewById(R.id.tv_idCard)).setText(insuranceActivateInfo.getIdCard());
        ((TextView) contentView.findViewById(R.id.tv_mobile_phone)).setText(insuranceActivateInfo.getMobilePhone());
        ((TextView) contentView.findViewById(R.id.tv_frame_number)).setText(insuranceActivateInfo.getFrameNumber());
        ((TextView) contentView.findViewById(R.id.tv_machine_number)).setText(insuranceActivateInfo.getMachineNumber());

        contentView.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setTitle("激活申请信息确认");
        builder.setContentView(contentView);
        builder.setPositiveButton("去申请", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCommit(insuranceActivateInfo);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onCancel();
                        dialog.dismiss();
                    }
                }
        );
        return builder.create();
    }

    /**
     * 创建一个提示用户激活保险的提示框
     *
     * @param context
     * @param listener
     */
    public static CommonDialog createActivateInsuranceDialog(final Context context, final OnConfirmListener listener) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setCancelable(false);
        builder.setCanceledOnTouchOutside(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.contentview_ios_simple_style_single, null);
        TextView message = (TextView) contentView.findViewById(R.id.message);
        message.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setTitle("激活保险");
        builder.setContentView(contentView);
        builder.setPositiveButton("去激活", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onConfirm();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        return builder.create();
    }

    /**
     * 创建一个取消用户激活保险的提示框
     *
     * @param context
     * @param listener
     */
    public static CommonDialog createInsuranceActivateExitDialog(final Context context, final OnSelectListener listener) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setCancelable(false);
        builder.setCanceledOnTouchOutside(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.contentview_ios_simple_style_multi, null);
        TextView message = (TextView) contentView.findViewById(R.id.message);
        message.setGravity(Gravity.LEFT);
        message.setText(R.string.insurance_activate_exit_without_save);
        builder.setTitle("温馨提示");
        builder.setContentView(contentView);
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onSelect(true);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onSelect(false);
                        dialog.dismiss();
                    }
                }
        );
        return builder.create();
    }


    /**
     * 创建一个提示清理缓存的提示框
     *
     * @param context
     * @param listener
     */
    public static CommonDialog createClearCacheDialog(final Context context, String cacheSize, final OnConfirmListener listener) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.contentview_ios_simple_style_multi, null);
        TextView message = (TextView) contentView.findViewById(R.id.message);
        message.setGravity(Gravity.LEFT);
        builder.setTitle("清理缓存");
        message.setText("执行清理可为您节省" + cacheSize + "存储空间");
        builder.setContentView(contentView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (listener != null)
                            listener.onConfirm();
                    }
                }
        );
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        return builder.create();
    }

    /**
     * 创建一个带回调的提示框(单选项)
     *
     * @param context
     * @return
     */
    public static CommonDialog createRefreshDialog(Context context, String msg, final OnConfirmListener listener) {

        CommonDialog.Builder builder = new CommonDialog.Builder(context);
        builder.setCancelable(false);
        builder.setCanceledOnTouchOutside(false);
        builder.isHideNegativeButton(true);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.contentview_ios_simple_style, null);
        TextView message = (TextView) contentView.findViewById(R.id.message);
        message.setText(msg);
        builder.setContentView(contentView);
        builder.setTitle("温馨提示");
        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (listener != null)
                            listener.onConfirm();
                    }
                }
        );
        return builder.create();
    }

    public interface OnConfirmListener {

        void onConfirm();

    }

    public interface OnSelectListener {

        void onSelect(boolean isConfirm);

    }

    public interface OnCommitListener<T> {

        void onCommit(T data);

        void onCancel();

    }
}
