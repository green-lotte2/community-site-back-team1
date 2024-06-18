package kr.co.zeroPie.oauth2;

import java.util.Map;

public class GoogleInfo implements OAuth2MemberInfo {
    public GoogleInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    private final Map<String, Object> attributes;

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

}
