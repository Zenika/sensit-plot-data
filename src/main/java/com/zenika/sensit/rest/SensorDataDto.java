package com.zenika.sensit.rest;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.zenika.sensit.resources.Measure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SensorDataDto extends ResourceSupport {
    
    @Getter
    private List<Measure> content;
}
