package it.epicode.GestioneDispositiviAziendali.controller;

import com.cloudinary.Cloudinary;
import it.epicode.GestioneDispositiviAziendali.exception.BadRequestException;
import it.epicode.GestioneDispositiviAziendali.model.Dipendente;
import it.epicode.GestioneDispositiviAziendali.model.DipendenteRequest;
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
public class DipendenteController {
    @Autowired
    private DipendenteService dipendeteService;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/dipendenti")
    public Page<Dipendente> getAll(Pageable pageable){
        return dipendeteService.getAllDipendenti(pageable);
    }

    @GetMapping("/dipendenti/{id}")
    public Dipendente getDipendenteById(@PathVariable int id){
        return dipendeteService.getDipendenteById(id);
    }

    @PostMapping("/dipendenti")
    public Dipendente saveDipendente(@RequestBody @Validated DipendenteRequest dipendenteRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return dipendeteService.saveDipendente(dipendenteRequest);
    }

    @PutMapping("/dipendenti/{id}")
    public Dipendente updateDipendente(@PathVariable int id, @RequestBody @Validated DipendenteRequest dipendenteRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        return dipendeteService.updateDipendente(id, dipendenteRequest);
    }

    @DeleteMapping("/dipendenti/{id}")
    public void deleteDipendente(@PathVariable int id){
            dipendeteService.deleteDipendente(id);
    }

    @PatchMapping("/dipendenti/{id}/upload")
    public Dipendente uploadImgProfilo(@PathVariable int id, @RequestParam("upload") MultipartFile file) throws IOException {
        return dipendeteService.uploadImgProfilo(id,
                (String) cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));

    }


}
