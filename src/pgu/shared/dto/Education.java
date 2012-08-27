package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Education implements IsSerializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

}
