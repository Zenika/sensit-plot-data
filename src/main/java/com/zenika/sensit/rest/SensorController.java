package com.zenika.sensit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zenika.sensit.Sensit;
import com.zenika.sensit.resources.Measure;
import com.zenika.sensit.resources.SensitResponse;
import com.zenika.sensit.resources.Sensor;

@RestController()
@RequestMapping("/api")
public class SensorController {
    
    @Autowired
    private Sensit sensit;
    
    @Value("${sensit.device.id}")
    private String deviceId;
    
    @RequestMapping("/sensors/motion")
    public ResponseEntity<List<Measure>> getMotionSensor() {
        
        String sensorId = sensit.getDevice(deviceId).getData().getMotionSensor().getId();
        
        SensitResponse<Sensor> response = sensit.getSensor(deviceId, sensorId);
        
        return new ResponseEntity<List<Measure>>(response.getData().getHistory(), HttpStatus.OK);
    }
    
    @RequestMapping("/sensors/temperature")
    public ResponseEntity<List<Measure>> getTemperatureSensor() {
        
        String sensorId = sensit.getDevice(deviceId).getData().getTemperatureSensor().getId();
        
        SensitResponse<Sensor> response = sensit.getSensor(deviceId, sensorId);
        
        return new ResponseEntity<List<Measure>>(response.getData().getHistory(), HttpStatus.OK);
    }
    
}
