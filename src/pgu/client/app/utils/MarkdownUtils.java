package pgu.client.app.utils;

public class MarkdownUtils {

    public static boolean isLoaded = false;

    public static native void initShowdownConverter() /*-{
        $wnd.pgu_geo.showdown_converter = new $wnd.Showdown.converter();
    }-*/;

    public static native String markdown(String text) /*-{
        return $wnd.pgu_geo.showdown_converter.makeHtml(text || '');
    }-*/;


}
