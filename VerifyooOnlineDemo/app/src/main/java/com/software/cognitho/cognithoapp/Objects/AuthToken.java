package com.software.cognitho.cognithoapp.Objects;

import java.util.HashMap;

import flexjson.JSONDeserializer;

/**
 * Created by roy on 9/30/2015.
 */
public class AuthToken {

    public AuthToken(String strAuthToken) {
        JSONDeserializer<HashMap> deserializer = new JSONDeserializer<HashMap>();
        HashMap hashAuthToken = deserializer.deserialize(strAuthToken);

        if (hashAuthToken.containsKey("AuthTokenId") && hashAuthToken.containsKey("AuthTokenKey")) {
            TokenId = (String) hashAuthToken.get("AuthTokenId");
            TokenKey = (String) hashAuthToken.get("AuthTokenKey");
        }
    }

    public AuthToken(String tokenId, String tokenKey) {
        TokenId = tokenId;
        TokenKey = tokenKey;
    }

    public String TokenId;
    public String TokenKey;
}
