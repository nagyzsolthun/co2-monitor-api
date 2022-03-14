package hu.zsoltn.co2.security;

import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;

import java.security.KeyFactory;
import java.security.interfaces.ECPublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * Utils class for parsing PEM formatted EC keys, loosely based on https://www.baeldung.com/java-read-pem-file-keys
 */
public class ECKeyUtils {

  private static final String PUBLIC_KEY_BEGIN_HEADER = "-----BEGIN PUBLIC KEY-----";
  private static final String PUBLIC_KEY_END_HEADER = "-----END PUBLIC KEY-----";
  private static final String EMPTY = "";
  private static final String EC = "EC";

  @SneakyThrows
  public static ECPublicKey readPublicKey(final String path) {
    final byte[] encoded = loadKeyPem(path);
    final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
    return (ECPublicKey) KeyFactory.getInstance(EC).generatePublic(keySpec);
  }

  @SneakyThrows
  private static byte[] loadKeyPem(final String fileName) {
    final String key = new String(new ClassPathResource(fileName).getInputStream().readAllBytes());
    final String keyPem = key
        .replace(PUBLIC_KEY_BEGIN_HEADER, EMPTY)
        .replace(PUBLIC_KEY_END_HEADER, EMPTY)
        .replaceAll(System.lineSeparator(), EMPTY);
    final byte[] encoded = Base64.decodeBase64(keyPem);
    return encoded;
  }


}
