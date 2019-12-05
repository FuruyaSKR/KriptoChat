/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexao;

import Sockets.GerenciadorClientes;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Furuya
 */
public class Criptografica {
    
    private static SecretKey chave;
    private static byte [] mgsCriptografada;
    private static String msg;
   

    public SecretKey getChave() {
        return chave;
    }

    public void setChave(SecretKey chave) {
        this.chave = chave;
    }

    public byte[] getMgsCriptografada() {
        return mgsCriptografada;
    }

    public void setMgsCriptografada(byte[] mgsCriptografada) {
        this.mgsCriptografada = mgsCriptografada;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public byte[] Criptografa() {
  
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
            chave = keyGenerator.generateKey();
            Cipher cipher;
            cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            mgsCriptografada = cipher.doFinal(msg.getBytes());
            
            // System.out.println("Mensagem Criptografada: " + mgsCriptografada);

        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Main_Teste.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(Criptografica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mgsCriptografada;
    
    }

    public String Descriptografa() {
        
        System.out.println("Mensagem Criptografada enviada");
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, chave);
           
            byte[] mgsDescriptografada = cipher.doFinal(mgsCriptografada);
            msg = new String(mgsDescriptografada);
            // System.out.println("Mensagem Descriptografada: " + msg);
            
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Main_Teste.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(Criptografica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return msg;
    }
 
    @Override
    public String toString() {
        return "Back{" + "chave=" + chave + ", mgsCriptografada=" + mgsCriptografada + ", msg=" + msg + '}';
    }

}

