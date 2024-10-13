package su.zhenya.me.security.core.provider;

public interface AuthenticationServerUri {

    String getReleaseTokenServerUri();
    String getVerifyTokenServerUri();
    String getSuspendTokenServerUri();
}
