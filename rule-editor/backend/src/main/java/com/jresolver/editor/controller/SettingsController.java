package com.jresolver.editor.controller;

import com.jresolver.editor.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class SettingsController {

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/rest/location", method = RequestMethod.POST)
    public ResponseEntity<Void> changeLocation(@RequestParam(value = "path", required = false) String path) {
        locationService.setPath(path);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
