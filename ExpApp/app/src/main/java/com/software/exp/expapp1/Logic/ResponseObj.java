package com.software.exp.expapp1.Logic;

import java.util.HashMap;

/**
 * Created by roy on 8/23/2015.
 */
public class ResponseObj {
    public boolean message;
    public String games;
    public String name;
    public String instruction;

    public ResponseObj(HashMap<String, Object> hashResponse) {
        message = Boolean.parseBoolean(hashResponse.get("message").toString());
        games = hashResponse.get("games").toString();
        name = hashResponse.get("Name").toString();
        instruction = hashResponse.get("Instruction").toString();
    }
}
