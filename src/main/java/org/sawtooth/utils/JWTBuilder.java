package org.sawtooth.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.sawtooth.models.jwtroomlink.JWTRoomLinkHeader;
import org.sawtooth.models.jwtroomlink.JWTRoomLinkPayload;
import org.sawtooth.models.jwtverification.JWTVerificationHeader;
import org.sawtooth.models.jwtverification.JWTVerificationPayload;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class JWTBuilder {
    private final String key = "TESServerPrivateKey";

    private String GetRoomLinkHeader() throws JsonProcessingException {
        JWTRoomLinkHeader header = new JWTRoomLinkHeader("JWT", "HS256");
        ObjectWriter objectWriter = new ObjectMapper().writer();
        return Base64.getEncoder().encodeToString(objectWriter.writeValueAsBytes(header));
    }

    private String GetRoomLinkPayload(int roomID) throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        JWTRoomLinkPayload payload = new JWTRoomLinkPayload("TESServer", calendar.getTimeInMillis(), roomID);
        ObjectWriter objectWriter = new ObjectMapper().writer();
        return Base64.getEncoder().encodeToString(objectWriter.writeValueAsBytes(payload));
    }

    private String GetRoomLinkPayload(int roomID, long exp) throws JsonProcessingException {
        JWTRoomLinkPayload payload = new JWTRoomLinkPayload("TESServer", exp, roomID);
        ObjectWriter objectWriter = new ObjectMapper().writer();
        return Base64.getEncoder().encodeToString(objectWriter.writeValueAsBytes(payload));
    }

    public String GetRoomLinkSignature(int roomID, long exp) throws JsonProcessingException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return new String(Hex.encode(digest.digest(String.join(".", GetRoomLinkHeader(),
            GetRoomLinkPayload(roomID, exp)).getBytes())));
    }

    public String GetRoomLinkToken(int roomID) throws JsonProcessingException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String token = String.join(".", GetRoomLinkHeader(), GetRoomLinkPayload(roomID));
        return String.join(".", token, new String(Hex.encode(digest.digest(token.getBytes()))));
    }

    private String GetVerificationHeader() throws JsonProcessingException {
        JWTVerificationHeader header = new JWTVerificationHeader("JWT", "HS256");
        ObjectWriter objectWriter = new ObjectMapper().writer();
        return Base64.getEncoder().encodeToString(objectWriter.writeValueAsBytes(header));
    }

    private String GetVerificationPayload(int customerID) throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 2);
        JWTVerificationPayload payload = new JWTVerificationPayload("TESServer", calendar.getTimeInMillis(), customerID);
        ObjectWriter objectWriter = new ObjectMapper().writer();
        return Base64.getEncoder().encodeToString(objectWriter.writeValueAsBytes(payload));
    }

    private String GetVerificationPayload(int customerID, long exp) throws JsonProcessingException {
        JWTVerificationPayload payload = new JWTVerificationPayload("TESServer", exp, customerID);
        ObjectWriter objectWriter = new ObjectMapper().writer();
        return Base64.getEncoder().encodeToString(objectWriter.writeValueAsBytes(payload));
    }

    public String GetVerificationSignature(int customerID, long exp) throws JsonProcessingException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return new String(Hex.encode(digest.digest(String.join(".", GetVerificationHeader(),
                GetVerificationPayload(customerID, exp)).getBytes())));
    }

    public String GetVerificationToken(int customerID) throws JsonProcessingException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String token = String.join(".", GetVerificationHeader(), GetVerificationPayload(customerID));
        return String.join(".", token, new String(Hex.encode(digest.digest(token.getBytes()))));
    }
}
