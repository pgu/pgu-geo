package pgu.client.app;

public class AppContext {

    public boolean isMapsApiLoaded;
    public boolean isChartsApiLoaded;
    public boolean isShowdownLoaded;

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

}
