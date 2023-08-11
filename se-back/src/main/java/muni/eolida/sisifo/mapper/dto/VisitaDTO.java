package muni.eolida.sisifo.mapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class VisitaDataTransferObject implements Serializable {
    private String id;
    private String ip; // "190.183.122.8",
    private String hostname; // 190.183.122.8
    private String continent_code; // "SA",
    private String continent_name; // "South America",
    private String country_code2; // "AR",
    private String country_code3; // "ARG",
    private String country_name; // "Argentina",
    private String country_capital; // "Buenos Aires",
    private String state_prov; // "Buenos Aires Autonomous City",
    private String district; // "Villa Ortúzar",
    private String city; // "Buenos Aires City",
    private String zipcode; // "1427",
    private String latitude; // -34.58605,
    private String longitude; // -58.47091,
    private String is_eu; // "false",
    private String calling_code; // "+54",
    private String country_tld; // ".ar",
    private String languages; // "es-AR,en,it,de,fr,gn",
    private String country_flag; // "https://ipgeolocation.io/static/flags/ar_64.png",
    private String isp; // "CABLE VISIÃN CRESPO SRL",
    private String connection_type; // "",
    private String organization; // "Gigared S.A.",
    private String asn; // "AS20207",
    private String geoname_id; // 3432082,
    private UsuarioDataTransferObject visitante;
}
