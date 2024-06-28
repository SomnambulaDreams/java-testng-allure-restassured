package utils.data;

import utils.rest.RestParameter;

import java.util.LinkedList;
import java.util.List;


public enum Header {

    ACCEPT          ("accept", "*/*"),
    ACCEPT_ENCODING ("accept-encoding", "gzip, deflate"),
    ACCEPT_LANGUAGE ("accept-language", "en-US,en;q=0.9,ru;q=0.8,uk;q=0.7"),
    CONNECTION      ("connection", "keep-alive"),
    DNT             ("dnt", "1"),
    USER_AGENT      ("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");

    public final String key;
    public final String defaultValue;


    Header(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }


    public static List<RestParameter> getDefaultHeaders() {
        List<RestParameter> result = new LinkedList<>();
        for (Header item : Header.values())
            result.add(new RestParameter(item.key, item.defaultValue));
        return result;
    }

    public static List<RestParameter> getDefaultHeaders(String host, String referer) {
        List<RestParameter> result = getDefaultHeaders();
        result.add(new RestParameter("host", host));
        result.add(new RestParameter("referer", referer));
        return result;
    }
}
