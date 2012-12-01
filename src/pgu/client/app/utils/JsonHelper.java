package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class JsonHelper {

    public native JavaScriptObject copy(JavaScriptObject jso) /*-{

        var str = this.@pgu.client.app.utils.JsonHelper::stringify(Lcom/google/gwt/core/client/JavaScriptObject;)
                       (jso);
        return JSON.parse(str);

    }-*/;

    public native String stringify(JavaScriptObject jso) /*-{

        return JSON.stringify(jso, function(key, value) {
            if (key == '__gwt_ObjectId') {
                return;
            }
            return value;
        });
    }-*/;

}
