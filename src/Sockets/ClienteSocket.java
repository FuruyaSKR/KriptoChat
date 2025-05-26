/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Furuya
 */
public class ClienteSocket {

    public static void main(String[] args) {
        try {
            final Socket cliente = new Socket("127.0.0.1", 8888);

            // Gera o par de chaves
            KeyPair parChaves = RSAUtil.generateKeyPair();
            PublicKey chavePublica = parChaves.getPublic();
            PrivateKey chavePrivada = parChaves.getPrivate();

            PrintWriter escritor = new PrintWriter(cliente.getOutputStream(), true);
            BufferedReader leitorSocket = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            BufferedReader leitorTerminal = new BufferedReader(new InputStreamReader(System.in));

            // Thread para receber mensagens
            new Thread(() -> {
                try {
                    String mensagem;
                    while ((mensagem = leitorSocket.readLine()) != null) {
                        // Aqui futuramente podemos detectar se a mensagem é criptografada
                        System.out.println(" " + mensagem);
                    }
                } catch (IOException e) {
                    System.out.println("Falha no servidor");
                    System.exit(0);
                }
            }).start();

            // Envia a chave pública após o login (espera prompt do servidor)
            String linha;
            while ((linha = leitorSocket.readLine()) != null) {
                System.out.println(" " + linha);

                if (linha.contains("Digite seu Nickname")) {
                    String nickname = leitorTerminal.readLine();
                    escritor.println(nickname);

                    // Envia chave pública em Base64 para o servidor
                    String chaveBase64 = RSAUtil.publicKeyToBase64(chavePublica);
                    escritor.println("/chave " + chaveBase64);
                } else if (linha.equals("LOGIN NEGADO")) {
                    System.out.println("Nickname inválido ou já em uso. Tente novamente.");
                } else if (linha.equals("LOGIN ACEITO")) {
                    System.out.println("Login realizado com sucesso.");
                    break; // login concluído
                }
            }

            String mensagemTerminal;
            while ((mensagemTerminal = leitorTerminal.readLine()) != null) {
                if (mensagemTerminal.isEmpty())
                    continue;
                escritor.println(mensagemTerminal);
                if (mensagemTerminal.equalsIgnoreCase(Comandos.SAIR))
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ou gerar chave.");
        }
    }
}
