package br.com.record.lgpd.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EnumSGBDConverter implements AttributeConverter<EnumSGBD, Long> {

	@Override
    public Long convertToDatabaseColumn(EnumSGBD enumSGBD) {
        if (enumSGBD == null) {
            return null;
        }
        Long id = enumSGBD.getId();
        return id;
    }

    @Override
    public EnumSGBD convertToEntityAttribute(Long dbIdEnumSGBD) {
        if (dbIdEnumSGBD == null ) {
            return null;
        }
        EnumSGBD enumSGBD = EnumSGBD.INDEFINIDO;        
        return enumSGBD.getEnumSGBDFromId(dbIdEnumSGBD.intValue());
    }	

}
