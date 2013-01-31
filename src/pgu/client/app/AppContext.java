package pgu.client.app;

public class AppContext {

    private boolean isMapsApiLoaded;
    private boolean isChartsApiLoaded;
    private boolean isShowdownLoaded;
    private boolean areContactsLoaded;
    private boolean isProfileLoaded;

    public boolean isMapsApiLoaded() {
        return isMapsApiLoaded;
    }

    public void setMapsApiLoaded(final boolean isMapsApiLoaded) {
        this.isMapsApiLoaded = isMapsApiLoaded;
    }

    public boolean isChartsApiLoaded() {
        return isChartsApiLoaded;
    }

    public void setChartsApiLoaded(final boolean isChartsApiLoaded) {
        this.isChartsApiLoaded = isChartsApiLoaded;
    }

    public boolean isShowdownLoaded() {
        return isShowdownLoaded;
    }

    public void setShowdownLoaded(final boolean isShowdownLoaded) {
        this.isShowdownLoaded = isShowdownLoaded;
    }

    public boolean areContactsLoaded() {
        return areContactsLoaded;
    }

    public void setContactsLoaded(final boolean areContactsLoaded) {
        this.areContactsLoaded = areContactsLoaded;
    }

    public boolean isProfileLoaded() {
        return isProfileLoaded;
    }

    public void setProfileLoaded(final boolean isProfileLoaded) {
        this.isProfileLoaded = isProfileLoaded;
    }

    public native String getProfileId() /*-{
        return $wnd.IN.User.getMemberId();
    }-*/;

}
