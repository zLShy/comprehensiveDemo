package http;

/**
 * Created by zhangli on 2019/7/20.
 */

public interface EnginCallback {
    //失败
    void onError(Exception e);

    //成功
    void onSuccess(String s);

    //默认
    public final EnginCallback DEFAULT_CALLBACK = new EnginCallback() {
        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String s) {

        }
    };
}
