package muni.eolida.sisifo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Entity
@RequiredArgsConstructor
@Setter
public class VisitModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitModel visitModel = (VisitModel) o;
        return Objects.equals(id, visitModel.id) && Objects.equals(ip, visitModel.ip) && Objects.equals(hostname, visitModel.hostname) && Objects.equals(continent_code, visitModel.continent_code) && Objects.equals(continent_name, visitModel.continent_name) && Objects.equals(country_code2, visitModel.country_code2) && Objects.equals(country_code3, visitModel.country_code3) && Objects.equals(country_name, visitModel.country_name) && Objects.equals(country_capital, visitModel.country_capital) && Objects.equals(state_prov, visitModel.state_prov) && Objects.equals(district, visitModel.district) && Objects.equals(city, visitModel.city) && Objects.equals(zipcode, visitModel.zipcode) && Objects.equals(latitude, visitModel.latitude) && Objects.equals(longitude, visitModel.longitude) && Objects.equals(is_eu, visitModel.is_eu) && Objects.equals(calling_code, visitModel.calling_code) && Objects.equals(country_tld, visitModel.country_tld) && Objects.equals(languages, visitModel.languages) && Objects.equals(country_flag, visitModel.country_flag) && Objects.equals(isp, visitModel.isp) && Objects.equals(connection_type, visitModel.connection_type) && Objects.equals(organization, visitModel.organization) && Objects.equals(asn, visitModel.asn) && Objects.equals(geoname_id, visitModel.geoname_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ip, hostname, continent_code, continent_name, country_code2, country_code3, country_name, country_capital, state_prov, district, city, zipcode, latitude, longitude, is_eu, calling_code, country_tld, languages, country_flag, isp, connection_type, organization, asn, geoname_id);
    }
}

