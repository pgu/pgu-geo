package pgu.client.app;

import pgu.client.app.utils.HasNotifications;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public interface AppView extends IsWidget //
        , AcceptsOneWidget //
        , HasNotifications //
{

    AcceptsOneWidget getHeader();

    AcceptsOneWidget getBody();

}
