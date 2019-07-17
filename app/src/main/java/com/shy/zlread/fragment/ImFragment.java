package com.shy.zlread.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONObject;

import io.rong.imkit.RongExtension;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.cs.CustomServiceManager;
import io.rong.imlib.model.Message;

/**
 * Created by zhangli on 2019/7/11.
 */

public class ImFragment extends ConversationFragment {

    //    private OnShowAnnounceListener onShowAnnounceListener;
//    private BottomEvaluateDialog dialog;
//    private List<SealCSEvaluateItem> mEvaluateList;
    private String mTargetId = "";
    private RongExtension rongExtension;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RongIMClient.getInstance().setCustomServiceHumanEvaluateListener(new CustomServiceManager.OnHumanEvaluateListener() {
            @Override
            public void onHumanEvaluate(JSONObject evaluateObject) {
                JSONObject jsonObject = evaluateObject;
//                SealCSEvaluateInfo sealCSEvaluateInfo = new SealCSEvaluateInfo(jsonObject);
//                mEvaluateList = sealCSEvaluateInfo.getSealCSEvaluateInfoList();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        rongExtension = (RongExtension) v.findViewById(io.rong.imkit.R.id.rc_extension);
        View messageListView = findViewById(v, io.rong.imkit.R.id.rc_layout_msg_list);
        listView = findViewById(messageListView, io.rong.imkit.R.id.rc_list);

        return v;
    }


    @Override
    protected void initFragment(Uri uri) {
        super.initFragment(uri);

    }

    @Override
    public void onReadReceiptStateClick(Message message) {
        super.onReadReceiptStateClick(message);

    }

    @Override
    public void onShowStarAndTabletDialog(String dialogId) {
        super.onShowStarAndTabletDialog(dialogId);
    }

    @Override
    public void onPluginToggleClick(View v, ViewGroup extensionBoard) {
        super.onPluginToggleClick(v, extensionBoard);

    }

    @Override
    public void onEmoticonToggleClick(View v, ViewGroup extensionBoard) {
        super.onEmoticonToggleClick(v, extensionBoard);

    }
}
