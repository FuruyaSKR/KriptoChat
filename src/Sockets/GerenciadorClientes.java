/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import Conexao.Criptografica;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

import java.net.Socket;
import java.util.Enumeration;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Furuya
 */
public class GerenciadorClientes extends Thread {

    private Socket cliente;
    private String nomeCliente;
    private BufferedReader leitor;
    private PrintWriter escritor;
    private static final Map<String, GerenciadorClientes> clientes = new HashMap<String, GerenciadorClientes>();
    private static Vector nomedetodos;

    public GerenciadorClientes(Socket cliente) {
        this.cliente = cliente;
        start();
    }

    /**
     *
     */
    @Override
    public void run() {

        try {
            leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            escritor = new PrintWriter(cliente.getOutputStream(), true);

            efetuarLogin();

            String msg;

            while (true) {

                msg = leitor.readLine();
                //System.out.println(msg);

                if (msg.equalsIgnoreCase(Comandos.SAIR)) {
                    this.cliente.close();
                } else if (msg.startsWith(Comandos.MENSAGEM)) {

                    String nomeDestinario = msg.substring(Comandos.MENSAGEM.length(), msg.length());
                    System.out.println("<" + this.nomeCliente + ">" + " está Cochichando para " + "<" + nomeDestinario + ">"); // informa o tell pro console do servidor
                    GerenciadorClientes destinario = clientes.get(nomeDestinario);

                    if (destinario == null) {
                        escritor.println("Usuario não encontrado!");
                    } else {
                        escritor.println("Escreva uma mensagem para " + destinario.getNomeCliente());
                        destinario.getEscritor().println("<" + this.nomeCliente + ">" + " está cochicando para você: " + leitor.readLine());
                    }

                } else if (msg.startsWith("/tellcript")) {

                    String nomeDestinario = msg.substring(Comandos.MENSAGEM_CRIPT.length(), msg.length());
                    System.out.println("<" + nomeDestinario + ">" + " está fazendo coisas erradas."); // informa o tellcript pro console do servidor
                    escritor.println(nomeDestinario);
                    GerenciadorClientes destinario = clientes.get(nomeDestinario);

                    if (destinario == null) {
                        escritor.println("Usuario não encontrado!");
                    } else {
                        escritor.println("Escreva uma mensagem para " + destinario.getNomeCliente());
                        Criptografica cod = new Criptografica();
                        cod.setMsg(leitor.readLine());
                        cod.Criptografa();
                        destinario.getEscritor().println("<" + this.nomeCliente + ">" + " está cochicando para você: " + cod.getMgsCriptografada());
                    }

                } else if (msg.startsWith("/descript")) {

                    String nomeDestinario = msg.substring(Comandos.MENSAGEM_DESCRIPT.length(), msg.length());
                    GerenciadorClientes destinario = clientes.get(nomeDestinario);
                    if (destinario == null) {
                        escritor.println("Usuario não encontrado!");
                    } else {
                        System.out.println(nomeDestinario + " descriptografou a mensagem de " + this.nomeCliente); // informa o tell pro console do servidor
                        Criptografica cod = new Criptografica();
                        cod.Descriptografa();
                        escritor.println("<" + msg + ">" + " mandou o seguinte: " + cod.getMsg());
                    }

                } else if (msg.equals(Comandos.LISTA_USUARIOS)) {
                    StringBuffer speed = new StringBuffer();
                    for (String c : clientes.keySet()) {
                        speed.append(c);
                        speed.append(",");
                    }
                    speed.delete(speed.length() - 1, speed.length());
                    escritor.println("Lista de usuarios on-line");
                    escritor.println(speed.toString());

                } else {
                    for (String c : clientes.keySet()) {
                        String nomeDestinario = c;

                        GerenciadorClientes destinario = clientes.get(nomeDestinario);
                        Criptografica cod = new Criptografica();
                        cod.setMsg(msg);
                        cod.Criptografa();
                        destinario.getEscritor().println("<" + this.nomeCliente + ">" + msg);

                    }

                }
            }
            
            }catch (IOException e) {
            System.err.println("Conexão Perdida");
            clientes.remove(this.nomeCliente);
            //e.printStackTrace();
        }

        }

    

    private synchronized void efetuarLogin() throws IOException {

        while (true) {
            escritor.println(Comandos.LOGIN_TELA);
            this.nomeCliente = leitor.readLine().replaceAll(",", "");
            if (this.nomeCliente.equalsIgnoreCase("null") || this.nomeCliente.isEmpty()) {
                escritor.println(Comandos.LOGIN_NEGADO);
            } else if (clientes.containsKey(this.nomeCliente)) {
                escritor.println(Comandos.LOGIN_NEGADO);
            } else {
                escritor.println(Comandos.LOGIN_ACEITO);
                System.out.println(this.nomeCliente + " está online.");
                escritor.println("Olá " + this.nomeCliente);
                clientes.put(this.nomeCliente, this);
                for (String cliente : clientes.keySet()) {
                    atualizarListaUsuarios(clientes.get(cliente));
                }
                break;
            }
        }
    }

    private void atualizarListaUsuarios(GerenciadorClientes cliente) {
        StringBuffer str = new StringBuffer();
        for (String c : clientes.keySet()) {
            if (cliente.getNomeCliente().equals(c)) {
                continue;
            }

            str.append(c);
            str.append(",");
        }
        if (str.length() > 0) {
            str.delete(str.length() - 1, str.length());
        }
    }

    public PrintWriter getEscritor() {
        return escritor;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    /*
     public BufferedReader getLeitor() {
     return leitor;
     }
     */
}
