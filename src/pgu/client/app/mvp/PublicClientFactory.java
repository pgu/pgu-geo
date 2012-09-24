package pgu.client.app.mvp;

import pgu.client.pub.PublicMenuView;
import pgu.client.pub.PublicView;
import pgu.client.service.PublicProfileServiceAsync;

public interface PublicClientFactory extends BaseClientFactory {

    PublicView getPublicView();

    PublicProfileServiceAsync getPublicProfileService();

    PublicMenuView getPublicMenuView();

}
