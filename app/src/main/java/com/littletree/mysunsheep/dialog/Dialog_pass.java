package com.littletree.mysunsheep.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littletree.mysunsheep.R;

/**
 * @ProjectName: MySunSheep
 * @Package: com.littletree.mysunsheep.dialog
 * @ClassName: Dialog_onebutton_notitle
 * @Author: littletree
 * @CreateDate: 2022/10/28/028 17:46
 */
public class Dialog_pass extends Dialog {

    public Dialog_pass(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {private Context context;
        private TextView dialog_title;
        private TextView btn_one;
        private ImageView iv_gif;

        public Builder(Context context) {
            this.context = context;
        }

        public TextView getDialog_title() {
            return dialog_title;
        }

        public void setDialog_title(TextView dialog_title) {
            this.dialog_title = dialog_title;
        }

        public TextView getBtn_one() {
            return btn_one;
        }

        public void setBtn_one(TextView btn_one) {
            this.btn_one = btn_one;
        }

        public ImageView getIv_gif() {
            return iv_gif;
        }

        public void setIv_gif(ImageView iv_gif) {
            this.iv_gif = iv_gif;
        }

        public Dialog_pass create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final Dialog_pass dialog = new Dialog_pass(context, R.style.SunDialog);
            View layout = inflater.inflate(R.layout.dialog_onebutton_notitle, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                    , android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            dialog_title = (TextView)layout.findViewById(R.id.dialog_title);
            btn_one = (TextView)layout.findViewById(R.id.btn_one);
            iv_gif = (ImageView) layout.findViewById(R.id.iv_gif);
            return dialog;
        }
    }
}
