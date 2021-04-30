package com.takeblip.testetakeblip.api.controller;

import com.takeblip.testetakeblip.api.entity.TakeBlip;
import com.takeblip.testetakeblip.api.service.TakeBlipService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpHeaders;
import java.util.List;

@RestController
@RequestMapping("/api/v1/takeblip")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TakeBlipController {

    @Autowired
    private final TakeBlipService takeBlipService;

    @GetMapping(value = "/{linguagem}")
    public List<TakeBlip> getRepositorios(@PathVariable String linguagem){
        return takeBlipService.getRepositorios(linguagem);
    }

}
