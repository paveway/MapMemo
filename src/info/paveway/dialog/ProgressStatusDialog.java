package info.paveway.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * 進捗状況表示ダイアログクラス
 *
 * @version 1.0 新規作成
 *
 */
public class ProgressStatusDialog extends DialogFragment {

    public static final String TITLE_KEY = "title";
    public static final String MESSAGE_KEY = "meesage";

    private static ProgressDialog mProgressDialog = null;

    // インスタンス生成はこれを使う
    public static ProgressStatusDialog newInstance(String title, String message) {
        ProgressStatusDialog instance = new ProgressStatusDialog();

        // ダイアログにパラメータを渡す
        Bundle arguments = new Bundle();
        arguments.putString(TITLE_KEY, title);
        arguments.putString(MESSAGE_KEY, message);
        instance.setArguments(arguments);

        return instance;
    }

    // ProgressDialog作成
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        if (mProgressDialog != null) {
            return mProgressDialog;
        }

        // パラメータを取得
        String title = getArguments().getString(MESSAGE_KEY);
        String message = getArguments().getString(MESSAGE_KEY);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        setCancelable(false);

        return mProgressDialog;
    }

    // progressDialog取得
    @Override
    public Dialog getDialog(){
        return mProgressDialog;
    }

    // ProgressDialog破棄
    @Override
    public void onDestroy(){
        super.onDestroy();
        mProgressDialog = null;
    }
}
