package sockets_java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

ublic class ClienteSocket {
    public static void main(String[] args) throws IOException {
        try{
        final Socket cliente = new Socket("Luciano-2016", 9999);
        
        //Lendo mensagens do servidor:
        new Thread(){
            public void run(){
                try {
                    BufferedReader leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    PrintWriter escritor  = new PrintWriter(cliente.getOutputStream());
                    
                    while(true){
                        String mensagem = leitor.readLine();
                        if (mensagem == null || mensagem.isEmpty())
                            continue;
                        System.out.println(mensagem);
                    }
                    
                } catch (IOException ex) {
                    System.out.println("Impossivel ler a mensagem do servidor!");
                    ex.printStackTrace();
                }
            }
        }.start();
        
        //Escrevendo mensagem para o servidor:
        PrintWriter escritor = new PrintWriter(cliente.getOutputStream(), true);
        BufferedReader leitorTerminal = new BufferedReader(new InputStreamReader(System.in));
        
        String mensagemTerminal = "";
        while (true){
            mensagemTerminal = leitorTerminal.readLine();
            if (mensagemTerminal == null || mensagemTerminal.length() == 0){
                continue;
            }    
            escritor.println(mensagemTerminal);
            
            if (mensagemTerminal.equalsIgnoreCase("SAIR")){
                System.exit(0);
            }
        }
                
        } catch (UnknownHostException e) {
            System.out.println("O endereço passado é inválido!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("O servidor pode estar fora do ar!");
            e.printStackTrace();
        }
    }
}
