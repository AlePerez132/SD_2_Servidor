/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestorbibliotecaservidor;

import gestorbibliotecacomun.GestorBibliotecaIntf;
import java.util.Scanner;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.rmi.registry.*;

/**
 *
 * @author alepd
 */
public class GestorBibliotecaServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            int Puerto = 8000;
            Registry registry = LocateRegistry.createRegistry(Puerto);
            GestorBibliotecaIntf obj = new GestorBibliotecaImpl();

            GestorBibliotecaIntf stub = (GestorBibliotecaIntf) UnicastRemoteObject.exportObject(obj, Puerto);

            registry = LocateRegistry.getRegistry(Puerto);
            registry.bind("GestorBiblioteca", stub);

            System.out.println("Servidor GestorBiblioteca esperando peticiones ... ");
        } catch (Exception e) {
            System.out.println("Error en GestorBiblioteca Calculadora:" + e);
        }
    }

}
