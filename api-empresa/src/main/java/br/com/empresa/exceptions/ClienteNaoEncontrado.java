package br.com.empresa.exceptions;

public class ClienteNaoEncontrado extends RuntimeException {
    public ClienteNaoEncontrado(String mmensagem) {
        super(mmensagem);
    }
}
