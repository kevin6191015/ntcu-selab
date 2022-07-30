package ntcu.selab.SpringServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
   
   
    @Bean
    public CorsFilter corsFilter() {
   
   
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允許源，這裡允許所有源訪問，實際應用會加以限制
        corsConfiguration.addAllowedOrigin("*");
        //允許所有請求頭
        corsConfiguration.addAllowedHeader("*");
        //允許所有方法
        corsConfiguration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
