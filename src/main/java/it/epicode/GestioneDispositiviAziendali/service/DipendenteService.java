package it.epicode.GestioneDispositiviAziendali.service;

import it.epicode.GestioneDispositiviAziendali.exception.NotFoundException;
import it.epicode.GestioneDispositiviAziendali.model.Dipendente;
import it.epicode.GestioneDispositiviAziendali.model.DipendenteRequest;
import it.epicode.GestioneDispositiviAziendali.model.Dispositivo;
import it.epicode.GestioneDispositiviAziendali.repository.DipendenteRepository;
import it.epicode.GestioneDispositiviAziendali.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    private DispositivoRepository dispositivoRepository;
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public Page<Dipendente> getAllDipendenti(Pageable pageable){

        return dipendenteRepository.findAll(pageable);
    }

    public Dipendente getDipendenteById(int id){
        return dipendenteRepository.findById(id).
                orElseThrow(()->new NotFoundException("Dipendente con id="+ id + " non trovato"));
    }

    public Dipendente saveDipendente(DipendenteRequest dipendenteRequest){
        Dipendente dipendente = new Dipendente(dipendenteRequest.getUsername(), dipendenteRequest.getNome(), dipendenteRequest.getCognome(), dipendenteRequest.getEmail());
        sendMail(dipendente.getEmail());
        return dipendenteRepository.save(dipendente);
    }

    private void sendMail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Servizio Rest");
        message.setText("Registrazione al servizio rest avvenuta con successo");

        javaMailSender.send(message);
    }
    public Dipendente updateDipendente(int id, DipendenteRequest dipendenteRequest) throws NotFoundException {
        Dipendente d = getDipendenteById(id);

        d.setUsername(dipendenteRequest.getUsername());
        d.setNome(dipendenteRequest.getNome());
        d.setCognome(dipendenteRequest.getCognome());
        d.setEmail(dipendenteRequest.getEmail());

        return dipendenteRepository.save(d);
    }

    public void deleteDipendente(int id) throws NotFoundException {
        Dipendente dipendente = getDipendenteById(id);
        for (Dispositivo dispositivo : dipendente.getDispositivi()) {
            dispositivo.setDipendente(null);
            dispositivoRepository.save(dispositivo);

        }
        dipendenteRepository.delete(dipendente);
    }

    public Dipendente uploadImgProfilo(int id, String url){
        Dipendente dipendente = getDipendenteById(id);
        dipendente.setImmagineProfilo(url);
        return dipendenteRepository.save(dipendente);
    }

}
