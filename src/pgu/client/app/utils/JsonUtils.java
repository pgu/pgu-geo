package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class JsonUtils {

    public static native String json_stringify(JavaScriptObject o) /*-{

        return JSON.stringify(o, function(key, value) {
            if (key == '__gwt_ObjectId') {
                return;
            }
            return value;
        });
    }-*/;


}
