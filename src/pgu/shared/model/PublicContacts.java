package pgu.shared.model;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PublicContacts implements IsSerializable {

    @Id
    private String userId;


}
