package com.nick.seller.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class SignService {

    private static Map<String,String> PUBLIC_KEYS = new HashMap<>();
    static {
        PUBLIC_KEYS.put("1000","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsYmTLuYEvuTduISqEdqlXSRvj\n" +
                "GCHK1Puicr5W75xI025i5AsJ3D4LQU5T36yDCQJ/A/wAU2GN5wkYXwADfU/goKxs\n" +
                "EiSx1dW+ufxZbl+b2QJph9Fc/rMS4cI7znOOcsMEi4p1/IJCRQAL7gCOC1DWKXzj\n" +
                "VNd830n9rTw5yt9sTQIDAQAB");
    }

    public String getPublicKey(String authId){
        return PUBLIC_KEYS.get(authId);
    }
}
