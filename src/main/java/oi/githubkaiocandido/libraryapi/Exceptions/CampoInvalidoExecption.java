package oi.githubkaiocandido.libraryapi.Exceptions;

import lombok.Getter;

public class CampoInvalidoExecption extends RuntimeException {

    @Getter
    private String campo;

    public CampoInvalidoExecption(String campo, String msg){
        super(msg);
        this.campo = campo;
    }

}
