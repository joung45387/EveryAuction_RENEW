package com.joung45387.EveryAuction.Security.Oauth.UserInfo;

import java.io.Serializable;
import java.util.Map;

public interface OAuth2UserInfo extends Serializable {
    Map<String, Object> getAttributes();
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
