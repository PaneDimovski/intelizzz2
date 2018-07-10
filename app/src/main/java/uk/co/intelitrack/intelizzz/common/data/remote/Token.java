package uk.co.intelitrack.intelizzz.common.data.remote;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class Token {
    private int result;
    private String jsession;
    private String JSESSIONID;

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public int getResult() {
        return result;
    }

    public String getJsession() {
        return jsession;
    }
}