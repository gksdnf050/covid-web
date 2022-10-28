package com.covid.web.model.transfer.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SampleRequest {
    @NotNull
    Integer number;
}
