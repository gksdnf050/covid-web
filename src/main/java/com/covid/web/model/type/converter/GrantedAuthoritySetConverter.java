package com.covid.web.model.type.converter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.LinkedHashSet;
import java.util.Set;

@Converter
public class GrantedAuthoritySetConverter implements AttributeConverter<Set<GrantedAuthority>, String> {

    @Override
    public String convertToDatabaseColumn(Set<GrantedAuthority> attribute) {
        return StringUtils.collectionToCommaDelimitedString(attribute);
    }

    @Override
    public Set<GrantedAuthority> convertToEntityAttribute(String dbData) {
        return new LinkedHashSet<>(AuthorityUtils.commaSeparatedStringToAuthorityList(dbData));
    }
}
