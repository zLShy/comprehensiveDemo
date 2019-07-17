package com.shy.zlread;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.shy.zlread.adapter.ConversationListAdapterEx;
import com.shy.zlread.fragment.ContactsFragment;
import com.shy.zlread.fragment.DiscoverFragment;
import com.shy.zlread.fragment.ImFragment;
import com.shy.zlread.fragment.MineFragment;
import com.zl.map.Utils.BaseActicity;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class ImActivity extends BaseActicity {

    private BottomNavigationView mNavigationView;
    private FrameLayout mContainer;
    private List<Fragment> mFragmentList = new ArrayList<>();

    private Fragment mImFragment;
    private ContactsFragment mContactsFragment;
    private DiscoverFragment mDiscoverFragment;
    private MineFragment mineFragment;
    /**
     * 会话列表的fragment
     */
    private ConversationListFragment mConversationListFragment = null;
    private boolean isDebug;
    private Context mContext;
    private Conversation.ConversationType[] mConversationsTypes = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initDate() {

        selectFragment(0);
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                }
                return false;
            }
        });
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_im);
    }

    @Override
    public void initViews() {
        this.mNavigationView = (BottomNavigationView) findViewById(R.id.im_bottom_nav);
        this.mContainer = (FrameLayout) findViewById(R.id.im_container);
//        FragmentManager fragmentManage = getSupportFragmentManager();
//        ConversationListFragment fragement = (ConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
//        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                .appendPath("conversationlist")
//                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
//                .build();
//        fragement.setUri(uri);
    }


    private void selectFragment(int i) {
        FragmentTransaction ts = getSupportFragmentManager().beginTransaction();
        hideFragment(ts);
        switch (i) {
            case 0:
                if (mImFragment == null) {
                    mImFragment = initConversationList();
                    mFragmentList.add(mImFragment);
                    ts.add(R.id.im_container, mImFragment);
                } else {
                    ts.show(mImFragment);
                }
                break;
            case 1:
                if (mContactsFragment == null) {
                    mContactsFragment = new ContactsFragment();
                    mFragmentList.add(mContactsFragment);
                    ts.add(R.id.im_container, mContactsFragment);
                } else {
                    ts.show(mDiscoverFragment);
                }
                break;
            case 2:
                if (mDiscoverFragment == null) {
                    mDiscoverFragment = new DiscoverFragment();
                    mFragmentList.add(mDiscoverFragment);
                    ts.add(R.id.im_container, mDiscoverFragment);
                } else {
                    ts.show(mDiscoverFragment);
                }
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    mFragmentList.add(mineFragment);
                    ts.add(R.id.im_container, mineFragment);
                } else {
                    ts.show(mineFragment);
                }
                break;
            default:
                break;
        }

        ts.commitAllowingStateLoss();
    }

    public void hideFragment(FragmentTransaction ts) {
        if (mFragmentList != null && mFragmentList.size() > 0) {
            for (Fragment fragment : mFragmentList) {
                ts.hide(fragment);
            }
        }

    }


    private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            listFragment.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
            Uri uri;
            if (isDebug) {
                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM,
                        Conversation.ConversationType.DISCUSSION
                };

            } else {
                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM
                };
            }
            listFragment.setUri(uri);
            mConversationListFragment = listFragment;
            return listFragment;
        } else {
            return mConversationListFragment;
        }
    }

}
