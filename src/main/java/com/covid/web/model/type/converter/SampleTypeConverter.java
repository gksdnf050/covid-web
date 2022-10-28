package com.covid.web.model.type.converter;



import com.covid.web.model.type.SampleType;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class SampleTypeConverter extends AbstractCodeConverter<SampleType, String> {

    public SampleTypeConverter() {
        super(SampleType.class);
    }
}
