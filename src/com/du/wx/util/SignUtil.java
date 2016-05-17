package com.du.wx.util;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 *
 * @author jing
 */
public class SignUtil {
    
    //验证消息的来源是否来自微信
    public static boolean check(String signature, String timestamp, String nonceString, String token){
        String newSignature = createNewSignature(timestamp,nonceString,token) ;
        
        if(newSignature.equals(signature)){
            return true ;
        }
        
       return false ;
    }

    private static String createNewSignature(String timestamp, String nonceString, String token) {
        String[] arrays = new String[]{timestamp,nonceString,token} ;
        Arrays.sort(arrays);
        StringBuilder content = new StringBuilder() ;
        int arrayLength = arrays.length ;
        for(int i = 0 ; i < arrayLength ; i++){
            content.append(arrays[i] );
        }
        
        MessageDigest msgDigest = null ;
        String stmpStr = "" ;
        try {
            msgDigest = MessageDigest.getInstance("SHA-1") ;
            byte[] data =  msgDigest.digest(content.toString().getBytes()) ;
            stmpStr = convertToHexString(data) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return stmpStr ;
    }

    private static String convertToHexString(byte[] data) {
        StringBuilder builder = new StringBuilder() ;
        int dataLength = data.length ;
        for(int i = 0 ; i < dataLength ; i++){
            String hv = Integer.toHexString(0xff & data[i]) ;
            if(hv.length() < 2){
                builder.append(0) ;
            }
            builder.append(hv) ;
        }
        
        return  builder.toString() ;
    }
}

