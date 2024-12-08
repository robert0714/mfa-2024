package com.example.mfa.utils;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException; 
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
 

@Slf4j
public class GoogleAuthenticatorUtil {

    private static final int TIMEEXCURSION = 3;

    /**
     * 隨機生成一個密鑰
     * @return
     */
    public static String createSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        String secretKey = base32.encodeToString(bytes);
        return secretKey.toLowerCase();
    }

    /**
     * 產生一組TOTP
     * @return
     */
    public static String getTOTP(String secretKey, long time) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey.toUpperCase());
        String hexKey = Hex.encodeHexString(bytes);
        String hexTime = Long.toHexString(time);
        return TOTP.generateTOTP(hexKey, hexTime, "6");
    } 

    /**
     * 產生QRCode
     * @return
     */
    public static String createGoogleAuthQRCodeData(String secret, String account, String issuer) {
        String qrCodeData = "otpauth://totp/%s?secret=%s&issuer=%s";
        try {
            return String.format(qrCodeData, URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20"), URLEncoder.encode(secret, "UTF-8")
                                                                                                                               .replace("+", "%20"), URLEncoder.encode(issuer, "UTF-8").replace("+", "%20"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 驗證
     * @return
     */
    public static boolean verify(String secretKey, String code) {
        long time = System.currentTimeMillis() / 1000 / 30;
        for (int i = -TIMEEXCURSION; i <= TIMEEXCURSION; i++) {
            String totp = getTOTP(secretKey, time + i);
            if (code.equals(totp)) {
                return true;
            }
        }
        return false;
    }
    public static void createQRCode(String barCodeData, String filePath, int height, int width)  {
		try (FileOutputStream out = new FileOutputStream(filePath)) {
			BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE, width, height);	
			System.out.println(filePath);
			MatrixToImageWriter.writeToStream(matrix, "png", out);
		} catch (WriterException | IOException e) {
			log.error(e.getMessage(), e); 
		}
	}

	public static Optional<String> createQRCode(String barCodeData, int height, int width) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE, width, height);
			MatrixToImageWriter.writeToStream(matrix, "png", out);
			byte[] bytes = out.toByteArray();
			String base64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
			return Optional.of(base64);
		} catch (WriterException | IOException e) {
			log.error(e.getMessage(), e);
			return Optional.empty();
		}
	}

}
