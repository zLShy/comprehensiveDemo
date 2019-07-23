package http;

import java.util.Map;

/**
 * Created by zhangli on 2019/7/20.
 */

public interface IHttpEngine {
    //get请求
    void get(String url, Map<String, Object> params, EnginCallback callback);

    //post请求
    void post(String url, Map<String, Object> params, EnginCallback callback);
    //
    //
    //
}
