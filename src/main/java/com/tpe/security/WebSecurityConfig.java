package com.tpe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // Security katmanina bu clasimin konfigurasyon vlasi oldugunu soylutorum
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // sunu admin yapabilsin, std yapabilsin diyebilmek icin
//                                                  method seviyede yetkilendirme yapilacak
//                                              prePostEnabled = true : methodlarin uzerine annotation koyacagim
//baska bir yontemle stdController,adminController gibi classlar olusturup class seviyede yetkilendrme yaplabilr
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // bu classda amacimiz : AuthManager, Provider , PassEncoder larimi olusturup birbirleriyle tanistirmak

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//  http'den kaynakli csrf default aciktir, update gibi bazi islemlerde cakisma oldugu icin kapatiyoruz
//  csrf: mesela browser'da kotu niyetli bir siteye girdigimzde baska tab'da acik olan
//  banka sitesindeki bilgiler calinabilir csrf bunu engelliyor
        http.csrf().disable().  // csrf korumasini disable yapiyoruz
                authorizeHttpRequests().  // gelen butun requestleri yetkili mi diye kontrol edecegiz
                antMatchers("/",
                "index.html",
                "/css/*",
                "/js/*").permitAll(). // bu end-pointleri yetkili mi diye kontrol etme
                anyRequest(). // muaf tutulan end-pointler disinda gelen herhangi bir requesti
                authenticated(). // yetkili mi diye kontrol et
                and().
                httpBasic(); // bunu yaparkende Basic Auth kullanilacagini belirtiyoruz

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);//4-31 - seviye 4: en dusuk kriptolama
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // encoder ile tanistirdim
        authenticationProvider.setUserDetailsService(userDetailsService);//userDetailsService ile tanistirdim
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
