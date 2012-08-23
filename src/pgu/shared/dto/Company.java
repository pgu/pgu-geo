package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Company implements IsSerializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
