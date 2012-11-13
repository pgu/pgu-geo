package pgu.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface ResourcesApp extends ClientBundle {

    public static final ResourcesApp INSTANCE =  GWT.create(ResourcesApp.class);

    @Source("resourcesApp.css")
    public CssResourceApp css();

    public interface CssResourceApp extends CssResource {

        String chartWell();

    }

}
