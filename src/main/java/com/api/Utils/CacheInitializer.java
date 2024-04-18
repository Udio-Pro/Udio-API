package com.api.Utils;

import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @description:
 * @Author: xJh
 * @Date: 2024/4/11
 */
@Component
public class CacheInitializer {
    private final Cache<String, String> sessionCookieCache;
    private final Cache<String, String> sessionTokenCache;

    private final Set<String> udioCookieSet;

    @Autowired
    public CacheInitializer(Cache<String, String> sessionCookieCache,
                            Cache<String, String> sessionTokenCache,
                            Set<String> udioCookieSet
    ) {
        this.sessionCookieCache = sessionCookieCache;
        this.sessionTokenCache = sessionTokenCache;
        this.udioCookieSet= udioCookieSet;
    }

    @PostConstruct
    public void init() {
//        System.out.println("Init......");
//        sessionCookieCache.put("your_suno_session",  "your_suno_cookie");
//        udioCookieSet.add("your_udio_cookie");
        sessionCookieCache.put("sess_2fEL4XGcwsS5Nu6t6K9DVQvwmEw",  "__stripe_mid=747ed0ed-e379-40a3-bbf3-fc5a65a459b38e43b3; __cf_bm=JFXDzIOGOqxWEQa9M80KVvOhzzWlHYKzFkIyd0Bunus-17133610" +
                "72-1.0.1.1-..43hqSt2RfbbL4JdqoJH9VdMNx6QLgfZM6GTibRCp7NC1zmVaVgFUAkH315mmiPjhSQVflO0oiX6.GkpCRQGg; _cfuvid=bjqObO.y2wp6OAYkkyQR8VgN4C4T.8.sp4DjU6" +
                "XcSUc-1713361072258-0.0.1.1-604800000; __client=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImNsaWVudF8yZXVRZWdyeExqT3Q5U1NRNmhYQnpHeWFKYmoiLCJ" +
                "yb3RhdGluZ190b2tlbiI6ImNxcXl3ZzEycmhucWFybDV5cWh2bDBxb2dlc3FpdmtsdTBsMmMxYWsifQ.amo_crKuJbb-CZTerksvdAZ9VNn2X9rbbS435cEWmo9p2Mh931AGCCcoKhJjjtZx" +
                "h3NHz_7fCDg7NczcMFpFZh6qbFfFevHHf5ev93C2e4FYSv9dkYaRrDvcJ5RdnTbG2r_Xb9gIsCOOqyNzBBg6-Pz2ky5UvVB5WfgROKbRuzk0vNbHD2Op4lAeIXXyy5DxMJazxoyh0iyIww9O7" +
                "QhXDnwsK0mOsxhrICT2Z-ETITpqbXfvTI4UPXGNbCfrMjNgg-YcHK4iN4NZBWvvILJFAJnrtwwRCxEW3cwD9CvHaYPM4oIwK3sRk5iOVTCKVcT-r_oYpYCBil5zGHV-fp1uYA; __client_u" +
                "at=1713361099; mp_26ced217328f4737497bd6ba6641ca1c_mixpanel=%7B%22distinct_id%22%3A%20%22af7c6d4e-96d8-42ec-9705-ff62c5ada164%22%2C%22%24device_i" +
                "d%22%3A%20%2218ec7efe2c2985-0e1f7793de8d27-26001a51-1fa400-18ec7efe2c2985%22%2C%22%24initial_referrer%22%3A%20%22%24direct%22%2C%22%24initial_ref" +
                "erring_domain%22%3A%20%22%24direct%22%2C%22%24user_id%22%3A%20%22af7c6d4e-96d8-42ec-9705-ff62c5ada164%22%7D");
        sessionCookieCache.put("sess_2exJIh24jpJKM4NGWNYjjg05SCz","__client=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImNs" +
                "aWVudF8yZXhKSWJrbDVQSzU4V21VRDJTYUs5V0hMQjQiLCJyb3RhdGluZ190b2tlbiI6InVuNnF3dm9wN3J6MXAyam5uamxyb2F5ZG54OHd3NTZqYjc2Y" +
                "3N4aWIifQ.f-a0QnDYUGBUBxwAbx0rBSBIrirIR-l8_9xqac3uGzrtVUJ93RkBJHQZO2SwnwEp908J5YBvePuJrpnKj6YXPFKpN_ux6QiCfQGcKrwmbjOrCgn" +
                "ZUiFthFWEZzoGxd7RlRLwd6Uts1vBYAdyayv2Lpc7FCe14J1jmHxLgKEQtbVBsW7FsoCN7t59tgHJlxyU93H8DGfj3wwaMiY8Pflc0Zgm2hSArdbi9WTAJuJGQDp5lkuesH" +
                "xm7LPwzxiLsuX_qTxl2HI2oBzd8h9GVAzYJEMLWFb0ShQH8Qgkj32dY4B22084wnJX_YE-7AN12SfLB9M4_9y9Jpf4Ra_ygFHkVw; __client_uat=1712840217; __cf_bm=" +
                "iKq3IxoHELMp.sSO4Ln3hs5nA7KA8HPDwCSCzok4pL0-1712844908-1.0.1.1-XRabh3KpTdia1VHPIDp2K7gHgWDVM4RSt0m8r_elt.1mzNWqTlPqxdsaajwMjA3sAb__GVp.lP3Du" +
                "Sm75oOsMA; _cfuvid=EYV9GwGMk1VKKI9K7..9YL.5t8MFwnBNu8.6RFKawBE-1712844908041-0.0.1.1-604800000; mp_26ced217328f4737497bd6ba6641ca1c_mixpanel=%7B%" +
                "22distinct_id%22%3A%20%22b0bc1906-337f-4c9b-9486-f72311900924%22%2C%22%24device_id%22%3A%20%2218ecd3b9e3d8ad-0e9a1d37204882-1d525637-168000-18ecd3b" +
                "9e3d8ad%22%2C%22%24initial_referrer%22%3A%20%22https%3A%2F%2Fsuno.com%2Fcreate%2F%22%2C%22%24initial_referring_domain%22%3A%20%22suno.com%22%2C%22%24use" +
                "r_id%22%3A%20%22b0bc1906-337f-4c9b-9486-f72311900924%22%7D");
        udioCookieSet.add("sb-api-auth-token=%5B%22eyJhbGciOiJIUzI1NiIsImtpZCI6IlJHVktoVzNNcSsyVzhxcDkiLCJ0eXAiOiJKV1QifQ.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoxNzEzMzMyMTQ5LCJpYXQiOjE3MTMzMjg1NDksImlzcyI6Imh0dHBzOi8vbWZ" +
                "tcHhqZW1hY3NoZmNwem9zbHUuc3VwYWJhc2UuY28vYXV0aC92MSIsInN1YiI6IjZhMzAxMGVkLWRhOWItNGU3Mi05M2Fk" +
                "LTI1MjZkYjM1MzQ3MyIsImVtYWlsIjoibGVlazAxNTI5OUBnbWFpbC5jb20iLCJwaG9uZSI6IiIsImFwcF9tZXRhZGF0YSI6eyJwcm92aWRlciI6Imdvb2dsZSIsInByb3ZpZGVycyI6WyJnb29nbGUiXX0sInVzZXJfbWV0YWRhdGEiOnsiYXZhdGFyX3VybCI6Imh0dHBzOi8v" +
                "bGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0xOcEowSGI5RHFfOElmUWhFSldxMHlnZDFKbnNnV204bGxINWl2U0tVWUpfVjBXX0k9czk2LWMiLCJlbWFpbCI6ImxlZWswMTUyOTl" +
                "AZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImZ1bGxfbmFtZSI6IuadjuWuhyIsImlzcyI6Imh0dHBzOi8vYWNjb3VudHMuZ29vZ2xlLmNvbSIsIm5hbWUiOiLmnY7lrociLCJ" +
                "waG9uZV92ZXJpZmllZCI6ZmFsc2UsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NMTnBKMEhiOURxXzhJZlFoRUpXcTB5Z2QxSm5zZ1dtOGx" +
                "sSDVpdlNLVVlKX1YwV19JPXM5Ni1jIiwicHJvdmlkZXJfaWQiOiIxMTM5NjM5NjQ5OTE3NzQ2ODM3MzMiLCJzdWIiOiIxMTM5NjM5NjQ5OTE3NzQ2ODM3MzMifSwicm9sZSI6ImF1dGhlbn" +
                "RpY2F0ZWQiLCJhYWwiOiJhYWwxIiwiYW1yIjpbeyJtZXRob2QiOiJvYXV0aCIsInRpbWVzdGFtcCI6MTcxMzMyODU0OX1dLCJzZXNzaW9uX2lkIjoiYjg2NWQ1N2QtZWJkMi00MTcxLTgzM" +
                "zctMDBiZjRkMmZkNTI0IiwiaXNfYW5vbnltb3VzIjpmYWxzZX0.RQ79j5Kj2RBoE11jfHZU22l5NXKH3s0wFCAMkpWSeRA%22%2C%22vvp3dSdkhMFxMJsCf72kiA%22%2C%22ya29.a0Ad52" +
                "N389f9FqEdM8f7klHXM61ylO6x0jGNHhZfLVlFKU-chGiIlnWyGxLvRfllEias0jloMctdl6O9_xKKkY3ow1t3wJIzfRNmeTffug9izHITGeXrSntQGpCPflHK3qKtY-PEEKP7GxxiI4N883P" +
                "V5S8-gLNIPrg7WFaCgYKAbsSARMSFQHGX2Mid4-tmsNrdPLp5foYRtO1sA0171%22%2Cnull%2Cnull%5D");
    }
}
