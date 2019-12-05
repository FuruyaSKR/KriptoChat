/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Furuya
 */
public class ServidorSocket {
    
    public static void main(String []args){
        
        ServerSocket servidor = null;
        
        try {
            System.out.println("Iniciando o Servidor...");
            servidor = new ServerSocket(8888);
            System.out.println("Server Open");
        
            while(true){
                Socket cliente = servidor.accept();
                new GerenciadorClientes(cliente);
            }
        
        } catch (IOException e) {
            
            try {
               if(servidor != null){
                   System.out.println("Server Closed");
                   servidor.close();
               }
            } catch (IOException e1) {}
                System.err.println("Esta Porta está sendo ocupada, ou  servidor foi fechado!");       
                //e.printStackTrace();
        }
    }
}
