package pgu.client.app.utils;

public class MarkdownUtils {

    public static native String markdown(String text) /*-{
        return $wnd.pgu_geo.showdown_converter.makeHtml(text || '');
    }-*/;


}
