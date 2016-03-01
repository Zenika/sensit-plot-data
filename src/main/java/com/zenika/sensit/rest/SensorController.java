package com.zenika.sensit.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zenika.sensit.Sensit;
import com.zenika.sensit.resources.Measure;
import com.zenika.sensit.resources.SensitResponse;
import com.zenika.sensit.resources.SensitResponse.Links;
import com.zenika.sensit.resources.Sensor;

@RestController()
@RequestMapping("/api")
public class SensorController {
    
    @Autowired
    private Sensit sensit;
    
    @Value("${sensit.device.id}")
    private String deviceId;
    
    @RequestMapping("/sensors/motion")
    public ResponseEntity<SensorDataDto> getMotionSensor(@RequestParam(defaultValue="1") Integer page) {
        
        
        String sensorId = sensit.getDevice(deviceId).getData().getMotionSensor().getId();
        SensitResponse<Sensor> sensor = sensit.getSensor(deviceId, sensorId, page);
        
        SensorDataDto response = new SensorDataDto(sensor.getData().getHistory());
        
        response.add(this.getMotionSensorLinks(sensor.getLinks(), page));
        
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping("/sensors/temperature")
    public ResponseEntity<SensorDataDto> getTemperatureSensor(@RequestParam(defaultValue="1") Integer page) {
        
        String sensorId = sensit.getDevice(deviceId).getData().getTemperatureSensor().getId();
        SensitResponse<Sensor> sensor = sensit.getSensor(deviceId, sensorId, page);
        
        SensorDataDto response = new SensorDataDto(sensor.getData().getHistory());
        
        response.add(this.getTemperatureSensorLinks(sensor.getLinks(), page));
        
        return ResponseEntity.ok().body(response);
    }
    
    private Iterable<Link> getTemperatureSensorLinks(Links sensorLinks, Integer curPage) {
        
        Integer lastPage = Optional.of(sensorLinks.getLast()).map(s -> Integer.parseInt(s.substring(s.length()-1))).orElse(1);
        Integer firstPage = 1;
        Integer nextPage = Math.min(lastPage, curPage+1);
        Integer prevPage = Math.max(1, curPage-1);
        
        List<Link> result = new ArrayList<>();
        result.add(linkTo(methodOn(SensorController.class).getTemperatureSensor(firstPage)).withRel("first"));
        result.add(linkTo(methodOn(SensorController.class).getTemperatureSensor(lastPage)).withRel("last"));
        if(nextPage != curPage)
            result.add(linkTo(methodOn(SensorController.class).getTemperatureSensor(nextPage)).withRel("next"));
        if(prevPage != curPage)
            result.add(linkTo(methodOn(SensorController.class).getTemperatureSensor(prevPage)).withRel("prev"));
        
        return result;
    }
    
    private Iterable<Link> getMotionSensorLinks(Links sensorLinks, Integer curPage) {
        
        Integer lastPage = Optional.of(sensorLinks.getLast()).map(s -> Integer.parseInt(s.substring(s.length()-1))).orElse(1);
        Integer firstPage = 1;
        Integer nextPage = Math.min(lastPage, curPage+1);
        Integer prevPage = Math.max(1, curPage-1);
        
        List<Link> result = new ArrayList<>();
        result.add(linkTo(methodOn(SensorController.class).getMotionSensor(firstPage)).withRel("first"));
        result.add(linkTo(methodOn(SensorController.class).getMotionSensor(lastPage)).withRel("last"));
        if(nextPage != curPage)
            result.add(linkTo(methodOn(SensorController.class).getMotionSensor(nextPage)).withRel("next"));
        if(prevPage != curPage)
            result.add(linkTo(methodOn(SensorController.class).getMotionSensor(prevPage)).withRel("prev"));
        
        return result;
    }
    
}
