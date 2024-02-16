package it.epicode.GestioneDispositiviAziendali.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
