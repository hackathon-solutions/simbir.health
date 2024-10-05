package su.zhenya.me.api.rest.controller.header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationHeader {

    private String authorization;

    public String getAuthorizationScheme() {
        return getAuthPair()[0];
    }

    public String getAuthorizationToken() {
        return getAuthPair()[1];
    }

    private String[] getAuthPair() {
        return this.authorization.split(" ");
    }
}
