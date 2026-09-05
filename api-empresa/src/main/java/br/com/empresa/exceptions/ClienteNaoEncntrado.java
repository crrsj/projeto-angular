package br.com.empresa.exceptions;

public class ClienteNaoEncntrado extends RuntimeException{
    public ClienteNaoEncntrado(String mensagem) {
        super(mensagem);
    }
}
