package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class JsonHelper {

    public native String stringify(JavaScriptObject o) /*-{

        return JSON.stringify(o, function(key, value) {
            if (key == '__gwt_ObjectId') {
                return;
            }
            return value;
        });
    }-*/;

}
