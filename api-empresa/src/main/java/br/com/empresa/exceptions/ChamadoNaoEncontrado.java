package br.com.empresa.exceptions;

public class ChamadoNaoEncontrado extends RuntimeException {
    public ChamadoNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}
