package it.epicode.GestioneDispositiviAziendali.controller;

import it.epicode.GestioneDispositiviAziendali.exception.BadRequestException;
import it.epicode.GestioneDispositiviAziendali.model.Dipendente;
import it.epicode.GestioneDispositiviAziendali.model.Dispositivo;
import it.epicode.GestioneDispositiviAziendali.model.DispositivoRequest;
import it.epicode.GestioneDispositiviAziendali.service.DipendenteService;
import it.epicode.GestioneDispositiviAziendali.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;
    @Autowired
    private DipendenteService dipendenteService;

    @GetMapping("/dispositivi")
    public Page<Dispositivo> getAll(Pageable pageable){

        return dispositivoService.getAllDispositivi(pageable);
    }
    @GetMapping("/dispositivi/{id}")
    public Dispositivo getDispositivoById(@PathVariable int id){
        return dispositivoService.getDispositiviById(id);

    }
    @PostMapping("/dispositivi")
    public Dispositivo saveDispositivo(@RequestBody @Validated DispositivoRequest dispositivoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return dispositivoService.saveDispositivo(dispositivoRequest);
    }
    @PutMapping("/dispositivi/{id}")
    public Dispositivo updateDispositivo(@PathVariable int id, @RequestBody @Validated DispositivoRequest dispositivoRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return dispositivoService.updateDispositivo(id, dispositivoRequest);
    }

    @DeleteMapping("/dispositivi/{id}")
    public void deleteDispositivo(@PathVariable int id){
        dispositivoService.deleteDispositivo(id);
    }

    @PatchMapping("/dispositivi/{id}/upload")
    public Dispositivo addDipendente(@PathVariable int id, @RequestParam("dipendeteId") int dipendenteId) throws IOException {

        return dispositivoService.addDipendente(id, dipendenteId);

    }
}

