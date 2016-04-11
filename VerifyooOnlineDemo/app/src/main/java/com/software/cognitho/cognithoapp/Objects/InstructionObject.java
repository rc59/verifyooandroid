package com.software.cognitho.cognithoapp.Objects;

import java.util.HashMap;

public class InstructionObject {
    public String Text;
    public String Id;
    public int InstructionIdx;
    public boolean IsInTemplate;

    public InstructionObject(HashMap hashMap) {
        IsInTemplate = false;
        if (hashMap.get("Text") != null) {
            Text = (String) hashMap.get("Text");
        }

        if (hashMap.get("_id") != null) {
            Id = (String) hashMap.get("_id");
        }

        if (hashMap.get("InstructionIdx") != null) {
            InstructionIdx = (Integer) hashMap.get("InstructionIdx");
        }
    }
}