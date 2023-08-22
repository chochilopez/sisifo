package muni.eolida.sisifo.util;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class OpenAPIConfig {
	@Bean
	public OpenAPI myOpenAPI() {
		Contact contact = new Contact();
		contact.setEmail("soporte@municrespo.gob.ar");
		contact.setUrl("https://www.crespo.gob.ar");
		contact.setExtensions(Collections.emptyMap());

		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Server prodServer = new Server();
		prodServer.setUrl("https://vps-3450851-x.dattaweb.com:9088");

		Info info = new Info()
				.title("API para la aplicacion de reclamos de la Municipalidad de Crespo")
				.version("1.0")
				.contact(contact)
				.description("Esta API expone todos los recursos necesarios para la realizacion de la APP de Reclamos.")
				.termsOfService("https://www.crespo.gob.ar/lanzamiento-del-concurso-de-desarrollo-de-aplicaciones-moviles-para-reclamos/")
				.extensions(Collections.emptyMap())
				.license(mitLicense);

		return new OpenAPI().info(info).servers(List.of(prodServer));
	}
}

