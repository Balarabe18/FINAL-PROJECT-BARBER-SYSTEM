package com.bms.bmsproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.bms.bmsproject.services.HairStylePhotoService;


@Controller
public class PhotoController {
    @Autowired
    private HairStylePhotoService hairStylePhotoService;

    @GetMapping("/hair-styles")
    public String getHairStyles(Model model) {
        model.addAttribute("hairStylePhotos", hairStylePhotoService.getHairStylePhotos());
        return "hair-styles";
    }

}
