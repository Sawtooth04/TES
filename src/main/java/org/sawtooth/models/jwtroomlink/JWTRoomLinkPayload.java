package org.sawtooth.models.jwtroomlink;

public record JWTRoomLinkPayload(String iss, long exp, int roomID) {
}
