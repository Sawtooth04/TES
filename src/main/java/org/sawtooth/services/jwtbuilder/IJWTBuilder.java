package org.sawtooth.services.jwtbuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.security.NoSuchAlgorithmException;

public interface IJWTBuilder {
    public String GetRoomLinkSignature(int roomID, long exp) throws JsonProcessingException, NoSuchAlgorithmException;

    public String GetRoomLinkToken(int roomID) throws JsonProcessingException, NoSuchAlgorithmException;

    public String GetVerificationSignature(int customerID, long exp) throws JsonProcessingException, NoSuchAlgorithmException;

    public String GetVerificationToken(int customerID) throws JsonProcessingException, NoSuchAlgorithmException;
}
