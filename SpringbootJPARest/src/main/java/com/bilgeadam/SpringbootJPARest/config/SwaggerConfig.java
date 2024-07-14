package com.bilgeadam.SpringbootJPARest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig
{
	@Bean
	@Lazy
	public OpenAPI springShopOpenAPI()
	{
		return new OpenAPI().info(new Info().title("Kitaplar kütüphane sistemi").description("Kitap ekle listele ara sil").version("V1.0").license(new License().name("Apache 2.0").url("localhost:8080/license"))).externalDocs(new ExternalDocumentation().description("Kitap rest işlemleri dökümantasyonu").url("localhost:8080/externaldocs"));
	}
}
