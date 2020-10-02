package com.nick.seller.sign;

import com.nick.seller.service.SignService;
import com.nick.util.RSAUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
@Aspect
public class SignAop {

    @Autowired
    private SignService signService;
    @Before(value = "execution(* com.nick.seller.controller.*.*(..)) && args(authId,sign,text,..)")
    public void verify(String authId,String sign,SignText text){
        String publicKey = signService.getPublicKey(authId);
        Assert.isTrue(RSAUtil.verify(text.toText(),sign,publicKey),"verify failed");

    }
}
