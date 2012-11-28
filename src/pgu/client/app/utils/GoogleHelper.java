package pgu.client.app.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class GoogleHelper {

    public native JavaScriptObject google() /*-{

    //  if (!$wnd.pgu_geo.google) {
    //      $wnd.console.log('google.caller');
    //      $wnd.console.log(arguments.callee.caller.caller.caller);
    //  }

      return $wnd.pgu_geo.google;
    }-*/;

}
