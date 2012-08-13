package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Country implements IsSerializable {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

}
