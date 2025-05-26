/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Furuya
 */
public class ServidorSocket {

    public static void main(String[] args) {
        ServerSocket servidor = null;
        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            System.out.println("Iniciando o Servidor...");
            servidor = new ServerSocket(8888);
            System.out.println("Server Open");

            while (true) {
                Socket cliente = servidor.accept();
                executor.submit(new GerenciadorClientes(cliente));
            }

        } catch (IOException e) {
            if (servidor != null) {
                try {
                    servidor.close();
                    executor.shutdown();
                } catch (IOException ex) {
                    System.err.println("Erro ao fechar servidor");
                }
            }
            System.err.println("Esta Porta está sendo ocupada, ou o servidor foi fechado!");
        }
    }
}
