package com.jjf.encryp;


import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * @author by jijianfeng on 2017/12/25.
 */
public class GZIP {
    public static void main(String[] args) throws IOException {
        String ss = "<stream><channel_cd>44081001</channel_cd><chnl_mchnt_cd>SIGN160ad5d7a58M4ZH8</chnl_mchnt_cd><rsp_code>00000</rsp_code><rsp_msg>SUCCESS</rsp_msg><sign_info>MIAGCSqGSIb3DQEHAqCAMIACAQExCzAJBgUrDgMCGgUAMIAGCSqGSIb3DQEHAQAAMYIBtjCCAbICAQEwMDArMQswCQYDVQQGEwJDTjENMAsGA1UECwwEUFROUjENMAsGA1UEAwwEdGVzdAIBMDAJBgUrDgMCGgUAoF0wGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTcxMjI1MDIzOTU5WjAjBgkqhkiG9w0BCQQxFgQUK722+A3TCLyQCz55DAZ9K5qojAMwDQYJKoZIhvcNAQEBBQAEggEAhMPEub6JRIeFSA1LwMvS6GNcAsti0xgwmwHvHsIpMpmvlgsBVB3vcPwFCfmDLmMC/YNwSIMFoSa8fo6Dqie24/f3m+QvDU1bltEArdHDRm9gMzZE7MDTOzFZRUcZVqsWpB7fW+K7XfhQexsIXjamI7gnSKwcgXecuyqCifGRGiQdrPvknQL7OwX1fgA9qWgQf0MlonefcH+61fhxw+hlE/LzXP1Z9IRU6Z764NnfFZz6nk+p4gB3imUq3zkWIMFGvIQuwnEsZNXPimnxoHctfzh6ed5+Q5x33wNeeMd1Beh3eb1mwPLO+cm1s9adU+Vw0aEQMVzs8b8f46FAMUVQZAAAAAAAAA==</sign_info></stream>";
        byte[] bytes = ss.getBytes();

        //∂‘bytes—πÀı
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(1024);
        GZIPOutputStream gzipOutput = new GZIPOutputStream(byteOutput, 1024);
        gzipOutput.write(bytes);
        gzipOutput.close();

        byte[] bytes2 = byteOutput.toByteArray();
        String encodeString = new BASE64Encoder().encode(bytes2);
        String byteString = byteOutput.toString("UTF-8");

        System.out.println(byteString);
        System.out.println();

        System.out.println(encodeString);
        System.out.println();
        System.out.println(ss.length()+" "+byteString.length()+" "+encodeString.length());
    }
}
