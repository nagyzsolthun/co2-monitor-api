package hu.zsoltn.co2.security;

import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.proc.SingleKeyJWSKeySelector;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.interfaces.ECPublicKey;

import static com.nimbusds.jose.JWSAlgorithm.ES256;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  @SneakyThrows
  protected void configure(HttpSecurity http) {
    http
        .sessionManagement().sessionCreationPolicy(STATELESS).and()
        .csrf().disable()
        .headers().frameOptions().sameOrigin()  // h2-console support
        .and().authorizeRequests(auth -> auth.antMatchers("/co2readings/**").authenticated())
        .oauth2ResourceServer(auth -> auth.jwt());
  }

  /**
   * Demo JWT configuration to support manually created JWT tokens
   * @return JwtDecoder instance using the key stored in resources/jwt/key.pub
   */
  @Bean
  @SneakyThrows
  public JwtDecoder jwtDecoder() {
    final ECPublicKey publicKey = ECKeyUtils.readPublicKey("jwt/key.pub");
    final JWSKeySelector<SecurityContext> keySelector = new SingleKeyJWSKeySelector<>(ES256, publicKey);
    final DefaultJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor();
    jwtProcessor.setJWSKeySelector(keySelector);
    return new NimbusJwtDecoder(jwtProcessor);
  }

}
