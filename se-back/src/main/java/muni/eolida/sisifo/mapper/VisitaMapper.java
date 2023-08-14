package muni.eolida.sisifo.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import muni.eolida.sisifo.helper.EntityMessenger;
import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.VisitaCreation;
import muni.eolida.sisifo.mapper.dto.VisitaDTO;
import muni.eolida.sisifo.model.VisitaModel;
import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.service.implementation.UsuarioServiceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VisitaMapper {
    private final UsuarioServiceImpl usuarioServiceImpl;
    private final UsuarioMapper usuarioMapper;

    public VisitaModel toEntity(VisitaCreation visitaCreation) {
        try {
            VisitaModel visitaModel = new VisitaModel();

            visitaModel.setIp(visitaCreation.getIp());
            visitaModel.setHostname(visitaCreation.getHostname());
            visitaModel.setContinent_code(visitaCreation.getContinent_code());
            visitaModel.setContinent_name(visitaCreation.getContinent_name());
            visitaModel.setCountry_code2(visitaCreation.getCountry_code2());
            visitaModel.setCountry_code3(visitaCreation.getCountry_code3());
            visitaModel.setCountry_name(visitaCreation.getCountry_name());
            visitaModel.setCountry_capital(visitaCreation.getCountry_capital());
            visitaModel.setState_prov(visitaCreation.getState_prov());
            visitaModel.setDistrict(visitaCreation.getDistrict());
            visitaModel.setCity(visitaCreation.getCity());
            visitaModel.setZipcode(visitaCreation.getZipcode());
            visitaModel.setLatitude(visitaCreation.getLatitude());
            visitaModel.setLongitude(visitaCreation.getLongitude());
            visitaModel.setIs_eu(visitaCreation.getIs_eu());
            visitaModel.setCalling_code(visitaCreation.getCalling_code());
            visitaModel.setCountry_tld(visitaCreation.getCountry_tld());
            visitaModel.setLanguages(visitaCreation.getLanguages());
            visitaModel.setCountry_flag(visitaCreation.getCountry_flag());
            visitaModel.setIsp(visitaCreation.getIsp());
            visitaModel.setConnection_type(visitaCreation.getConnection_type());
            visitaModel.setOrganization(visitaCreation.getOrganization());
            visitaModel.setAsn(visitaCreation.getAsn());
            visitaModel.setGeoname_id(visitaCreation.getGeoname_id());
            if (!Helper.isEmptyString(visitaCreation.getVisitante_id())) {
                EntityMessenger<UsuarioModel> visitante = usuarioServiceImpl.buscarPorNombreDeUsuario(visitaCreation.getVisitante_id());
                if (visitante.getStatusCode() == 200)
                    visitaModel.setVisitante(visitante.getObject());
            }

            return visitaModel;
        } catch (Exception e) {
            log.info("Visita creation to entity error. Exception: " + e);
            return null;
        }
    }

    public VisitaDTO toDto(VisitaModel visitaModel) {
        try {
            VisitaDTO dto = new VisitaDTO();

            dto.setIp(visitaModel.getIp());
            dto.setHostname(visitaModel.getHostname());
            dto.setContinent_code(visitaModel.getContinent_code());
            dto.setContinent_name(visitaModel.getContinent_name());
            dto.setCountry_code2(visitaModel.getCountry_code2());
            dto.setCountry_code3(visitaModel.getCountry_code3());
            dto.setCountry_name(visitaModel.getCountry_name());
            dto.setCountry_capital(visitaModel.getCountry_capital());
            dto.setState_prov(visitaModel.getState_prov());
            dto.setDistrict(visitaModel.getDistrict());
            dto.setCity(visitaModel.getCity());
            dto.setZipcode(visitaModel.getZipcode());
            dto.setLatitude(visitaModel.getLatitude());
            dto.setLongitude(visitaModel.getLongitude());
            dto.setIs_eu(visitaModel.getIs_eu());
            dto.setCalling_code(visitaModel.getCalling_code());
            dto.setCountry_tld(visitaModel.getCountry_tld());
            dto.setLanguages(visitaModel.getLanguages());
            dto.setCountry_flag(visitaModel.getCountry_flag());
            dto.setIsp(visitaModel.getIsp());
            dto.setConnection_type(visitaModel.getConnection_type());
            dto.setOrganization(visitaModel.getOrganization());
            dto.setAsn(visitaModel.getAsn());
            dto.setGeoname_id(visitaModel.getGeoname_id());
            if (visitaModel.getVisitante() != null)
                dto.setVisitante(usuarioMapper.toDto(visitaModel.getVisitante()));

            return dto;
        } catch (Exception e) {
            log.info("Visita entity to dto error. Exception: " + e);
            return null;
        }
    }
}