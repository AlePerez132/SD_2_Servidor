/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorbibliotecaservidor;

import gestorbibliotecacomun.*;

import java.util.ArrayList;

/**
 *
 * @author alepd
 */
public class Repositorio {
    private ArrayList<TLibro> libros;
    private TDatosRepositorio datos;

    public Repositorio() {
    }

    public Repositorio(ArrayList<TLibro> libros, TDatosRepositorio datos) {
        this.libros = libros;
        this.datos = datos;
    }

    public ArrayList<TLibro> getLibros() {
        return libros;
    }

    public void setLibros(ArrayList<TLibro> libros) {
        this.libros = libros;
    }

    public TDatosRepositorio getDatos() {
        return datos;
    }

    public void setDatos(TDatosRepositorio datos) {
        this.datos = datos;
    }
    
    
}
