package muni.eolida.sisifo.mapper;

import muni.eolida.sisifo.helper.Helper;
import muni.eolida.sisifo.mapper.creation.VisitCreation;
import muni.eolida.sisifo.mapper.dto.VisitDataTransferObject;
import muni.eolida.sisifo.model.VisitModel;
import muni.eolida.sisifo.service.implementation.LocalFileServiceImplementation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VisitMapper {
    private final LocalFileServiceImplementation localFileServiceImplementation;

    public VisitDataTransferObject toDto(VisitModel visitModel) {
        try {
            log.info("Visit entity to dto.");
            VisitDataTransferObject dto = new VisitDataTransferObject();

            dto.setId(visitModel.getId().toString());
            dto.setIp(visitModel.getIp());
            dto.setHostname(visitModel.getHostname());
            dto.setContinent_code(visitModel.getContinent_code());
            dto.setContinent_name(visitModel.getContinent_name());
            dto.setCountry_code2(visitModel.getCountry_code2());
            dto.setCountry_code3(visitModel.getCountry_code3());
            dto.setCountry_name(visitModel.getCountry_name());
            dto.setCountry_capital(visitModel.getCountry_capital());
            dto.setState_prov(visitModel.getState_prov());
            dto.setDistrict(visitModel.getDistrict());
            dto.setCity(visitModel.getCity());
            dto.setZipcode(visitModel.getZipcode());
            dto.setLatitude(visitModel.getLatitude());
            dto.setLongitude(visitModel.getLongitude());
            dto.setIs_eu(visitModel.getIs_eu());
            dto.setCalling_code(visitModel.getCalling_code());
            dto.setCountry_tld(visitModel.getCountry_tld());
            dto.setLanguages(visitModel.getLanguages());
            dto.setCountry_flag(visitModel.getCountry_flag());
            dto.setIsp(visitModel.getIsp());
            dto.setConnection_type(visitModel.getConnection_type());
            dto.setOrganization(visitModel.getOrganization());
            dto.setAsn(visitModel.getAsn());
            dto.setGeoname_id(visitModel.getGeoname_id());

            return dto;
        } catch (Exception e) {
            log.info("Visit entity to dto error. Exception: " + e);
            return null;
        }
    }

    public VisitModel toEntity(VisitCreation visitCreation) {
        try {
            log.info("Visit creation to entity.");
            VisitModel visitModel = new VisitModel();

            if (!Helper.isEmptyString(visitCreation.getId()))
                visitModel.setId(Helper.getLong(visitCreation.getId()));
            visitModel.setIp(visitCreation.getIp());
            visitModel.setHostname(visitCreation.getHostname());
            visitModel.setContinent_code(visitCreation.getContinent_code());
            visitModel.setContinent_name(visitCreation.getContinent_name());
            visitModel.setCountry_code2(visitCreation.getCountry_code2());
            visitModel.setCountry_code3(visitCreation.getCountry_code3());
            visitModel.setCountry_name(visitCreation.getCountry_name());
            visitModel.setCountry_capital(visitCreation.getCountry_capital());
            visitModel.setState_prov(visitCreation.getState_prov());
            visitModel.setDistrict(visitCreation.getDistrict());
            visitModel.setCity(visitCreation.getCity());
            visitModel.setZipcode(visitCreation.getZipcode());
            visitModel.setLatitude(visitCreation.getLatitude());
            visitModel.setLongitude(visitCreation.getLongitude());
            visitModel.setIs_eu(visitCreation.getIs_eu());
            visitModel.setCalling_code(visitCreation.getCalling_code());
            visitModel.setCountry_tld(visitCreation.getCountry_tld());
            visitModel.setLanguages(visitCreation.getLanguages());
            visitModel.setCountry_flag(visitCreation.getCountry_flag());
            visitModel.setIsp(visitCreation.getIsp());
            visitModel.setConnection_type(visitCreation.getConnection_type());
            visitModel.setOrganization(visitCreation.getOrganization());
            visitModel.setAsn(visitCreation.getAsn());
            visitModel.setGeoname_id(visitCreation.getGeoname_id());

            return visitModel;
        } catch (Exception e) {
            log.info("Visit creation to entity error. Exception: " + e);
            return null;
        }
    }
}
