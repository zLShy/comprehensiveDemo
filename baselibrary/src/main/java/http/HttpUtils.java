package http;

import android.content.Context;
import android.util.ArrayMap;

import java.util.Map;

/**
 * Created by zhangli on 2019/7/20.
 */

public class HttpUtils {

    private static IHttpEngine mHttpEngine = new OkHttpEngin();

    private Context mContext;
    private Map<String, Object> mParams;
    private String mUrl;
    private int mType = GET_TYPE;
    public static int POST_TYPE = 0x0011;
    public static int GET_TYPE = 0x0012;

    public HttpUtils(Context context) {
        this.mContext = context.getApplicationContext();
        this.mParams = new ArrayMap<>();
    }

    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    public void exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    public HttpUtils get() {
        mType = GET_TYPE;
        return this;
    }

    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }

    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    //添加参数
    public HttpUtils addParame(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    //添加参数
    public HttpUtils addParames(Map<String, Object> mParams) {
        mParams.putAll(mParams);
        return this;
    }

    //方法执行
    public void execute(EnginCallback callback) {

        if (callback == null) {
            callback = EnginCallback.DEFAULT_CALLBACK;
        }
        if (mType == POST_TYPE) {
            post(mUrl, mParams, callback);
        }
        if (mType == GET_TYPE) {
            get(mUrl, mParams, callback);
        }
    }

    public void execute() {
        execute(null);
    }

    public static HttpUtils with(Context context) {

        return new HttpUtils(context);
    }


    private void get(String url, Map<String, Object> params, EnginCallback callback) {

        mHttpEngine.get(url, params, callback);
    }

    private void post(String url, Map<String, Object> params, EnginCallback callback) {
        mHttpEngine.post(url, params, callback);
    }
}
