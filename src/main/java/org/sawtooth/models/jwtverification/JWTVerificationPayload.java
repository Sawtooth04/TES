package org.sawtooth.models.jwtverification;

public record JWTVerificationPayload(String iss, long exp, int customerID) {
}
