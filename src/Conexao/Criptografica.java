/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexao;

import Sockets.GerenciadorClientes;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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
    private static byte[] mgsCriptografada;
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

    public static String criptografar(String mensagem) {
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            byte[] criptografado = cipher.doFinal(mensagem.getBytes());
            return Base64.getEncoder().encodeToString(criptografado);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
                | NoSuchPaddingException ex) {
            Logger.getLogger(Criptografica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String descriptografar(String base64) {
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, chave);
            byte[] criptografado = Base64.getDecoder().decode(base64);
            byte[] descriptografado = cipher.doFinal(criptografado);
            return new String(descriptografado);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
                | NoSuchPaddingException ex) {
            Logger.getLogger(Criptografica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Back{" + "chave=" + chave + ", mgsCriptografada=" + mgsCriptografada + ", msg=" + msg + '}';
    }

}
