package pgu.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AccessToken implements IsSerializable {

    private String token;
    private String secret;
    private String rawResponse;

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(final String secret) {
        this.secret = secret;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(final String rawResponse) {
        this.rawResponse = rawResponse;
    }

}
