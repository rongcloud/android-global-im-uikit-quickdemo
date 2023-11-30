package com.rongcloud.im.uikit.quickdemo;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rongcloud.im.uikit.config.ConfigCenter;
import com.rongcloud.im.uikit.utils.RouteUtil;

import io.rong.imlib.IRongCoreCallback;
import io.rong.imlib.IRongCoreEnum;
import io.rong.imlib.RongCoreClient;
import io.rong.imlib.model.UserInfo;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btnLogin).setOnClickListener(view -> {
            String token = "后台获取的 token";
            connectIM(token);
        });
        setIMStatusListener();
    }

    private void connectIM(String token) {
        RongCoreClient.connect(token, new IRongCoreCallback.ConnectCallback() {
            @Override
            public void onSuccess(String userId) {
                // 登录成功，跳转到默认会话列表页。
                RouteUtil.routeToChatListActivity(LoginActivity.this);
            }

            @Override
            public void onError(IRongCoreEnum.ConnectionErrorCode code) {
            }

            @Override
            public void onDatabaseOpened(IRongCoreEnum.DatabaseOpenStatus code) {
            }
        });

        // 设置用户信息
        ConfigCenter.getUserInfoConfig().setUserInfoProvider(userId -> new UserInfo(userId, userId, Uri.parse("头像地址")), true);
    }

    private void setIMStatusListener() {
        RongCoreClient.addConnectionStatusListener(status -> {
            // 开发者需要根据连接状态码，进行不同业务处理
        });
    }
}
