package com.covid.web.model.transfer.response;

import com.covid.web.model.entity.Sample;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SampleResponse {
    List<Sample> samples = new ArrayList<>();
}
