/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexao;

import java.util.Scanner;

/**
 *
 * @author Furuya
 *
 */
public class Main_Teste {

    public static void main(String[] args) {
        boolean online = true;
        Scanner entrada = new Scanner(System.in);
        Criptografica cript = new Criptografica();
        System.out.println("\nAmbos usuarios conectados a rede local\n"
                + "Todas as mensagens aqui não ficaram salvas no sistema, além de serem criptografadas.\n"
                + "Assim negando qualquer acesso externo!");
        while (online == true) {
            System.out.println("\n1 - Mandar mensagem \n2 - Criptografar mensagem \n3 - Desccipitografar ");
            int menu = entrada.nextInt();
            switch (menu) {
                case 1:
                    System.out.print("Digite: ");
                    cript.setMsg(entrada.next());
                    break;
                case 2:
                    System.out.println(" --- Mensagem Criptografada ---");
                    System.out.print("Digite: ");
                    cript.setMsg(entrada.next());
                    cript.Criptografa();
                    break;
                case 3:
                    cript.Descriptografa();
                    break;
                default:
                    System.out.println("Opção incorreta, tente novamente!");
                    break;

            }

        }
    }

}
