package com.watcix.userauthdetails.dto;

public class JwtResponse {
    private String accessToken;
    private String token;

    public JwtResponse() {
    }

    public JwtResponse(String accessToken, String token) {
        this.accessToken = accessToken;
        this.token = token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public static JwtResponseBuilder builder() {
        return new JwtResponseBuilder();
    }

    public static final class JwtResponseBuilder {
        private String accessToken;
        private String token;

        private JwtResponseBuilder() {
        }

        public JwtResponseBuilder withAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public JwtResponseBuilder withToken(String token) {
            this.token = token;
            return this;
        }

        public JwtResponse build() {
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(accessToken);
            jwtResponse.setToken(token);
            return jwtResponse;
        }
    }
}
