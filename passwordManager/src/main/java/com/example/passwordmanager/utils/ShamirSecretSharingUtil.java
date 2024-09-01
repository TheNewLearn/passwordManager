package com.example.passwordmanager.utils;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class ShamirSecretSharingUtil {
    //切割數量
    @Value("${sss.mValue}")
    private int m;
    //重組數量
    @Value("${sss.kValue}")
    private int k;
    //質數p
    @Value("${sss.pValue}")
    private BigInteger p;

    private BigInteger[] generateCoefficient(){
        BigInteger[] randomCoefficient = new BigInteger[k-1];
        for(int i=0; i<k-1;i++){
            SecureRandom random = new SecureRandom();
            randomCoefficient[i] =  BigInteger.probablePrime(256,random).mod(p);
        }
        return randomCoefficient;
    }

    public List<String> splitSecret(String secret){
        BigInteger encoded_secret = new BigInteger(secret.getBytes(StandardCharsets.UTF_8));
        BigInteger[] coefficient = generateCoefficient();
        List<String> shares = new ArrayList<>();
        if (encoded_secret.compareTo(p) >= 0) {
            throw new IllegalArgumentException("Secret must be less than prime p");
        }
        for(int i=0;i<m;i++){
            BigInteger x = BigInteger.valueOf(i+1);
            BigInteger res = encoded_secret;
            for(int j=0;j<k-1;j++){
               res = res.add(coefficient[j].multiply(x.pow(j+1)));
            }
            shares.add(i+1 +"-"+ res.mod(p));
        }
        return shares;
    }

    public String secretDecode(List<String> shares){
        BigInteger buffer = BigInteger.ZERO;
        for(String str:shares){
            String[] xy = str.split("-");
            Set<BigInteger> lagrangeSet = shares.stream()
                    .filter(filterStr -> !filterStr.startsWith(xy[0]))
                    .map(mapStr -> new BigInteger(mapStr.split("-")[0]))
                    .collect(Collectors.toSet());
           buffer = buffer.add(Lagrange(lagrangeSet,xy[0],xy[1]));
        }
        buffer = buffer.mod(p);
        return new String(buffer.toByteArray(),StandardCharsets.UTF_8);
    }
    //計算基值
    private BigInteger Lagrange(Set<BigInteger> shares, String x, String y){
        BigInteger xi = new BigInteger(x);
        BigInteger yi = new BigInteger(y);
        BigInteger res = BigInteger.ONE;
        for(BigInteger xn: shares){
            res = res.multiply((BigInteger.ZERO.subtract(xn)).divide(xi.subtract(xn)));
        }
        return yi.multiply(res);
    }




}
