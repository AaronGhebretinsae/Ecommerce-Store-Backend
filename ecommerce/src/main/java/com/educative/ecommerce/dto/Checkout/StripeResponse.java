package com.educative.ecommerce.dto.Checkout;

public class StripeResponse {
    private String sessionId;

    public StripeResponse(String sessioId) {
        this.sessionId = sessioId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
