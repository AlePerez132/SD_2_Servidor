/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorbibliotecaservidor;

import gestorbibliotecacomun.*;
import java.rmi.RemoteException;

/**
 *
 * @author alepd
 */
public class GestorBibliotecaImpl implements GestorBibliotecaIntf {

    /*Se encarga de verificar la contraseña de administrador y devolverá un número (IDA) dependiendo de las
    siguientes condiciones:
    -1: Ya hay un usuario identificado como administrador.
    -2: La contraseña es errónea.
    N: Un número aleatorio generado en el intervalo [1, 1000000].
    Este número deberá ser utilizado en todas las operaciones de Administración en el campo Ida. */
    @Override
    public int Conexion(String pPasswd) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Comprueba que el Ida coincide con el almacenado en el servidor. Si no coincide devuelve false y caso
    contrario borra el Ida (lo pone a -1) almacenado en el servidor y devuelve true. */
    @Override
    public boolean Desconexion(int pIda) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Devuelve el número de repositorios almacenados en la biblioteca. En caso de que exista otro usuario
    identificado como administrador devolverá -1. */
    @Override
    public int NRepositorios(int pIda) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Devolverá los datos del repositorio cargado en la posición que indica el parámetro pRepo. La clase
    TDatosRepositorio almacena el nombre del repositorio, la dirección del Repositorio y el número de libros
    que contiene el repositorio. Las salidas del servicio es la siguiente:
    null: Ya hay un usuario identificado como administrador o el Ida no coincide con el almacenado
    en el Servidor.
    null: El repositorio cuya posición se indica no existe.
    ¡null: La referencia a un objeto TDatosRepositorio con los datos del repositorio. */
    @Override
    public TDatosRepositorio DatosRepositorio(int pIda, int pPosRepo) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Carga en memoria el repositorio cuyo nombre de fichero es pasado por parámetro. Las salidas del servicio
    son:
    -1: Ya hay un usuario identificado como administrador o el Ida no coincide con el almacenado en el
     Servidor.
    -2: Ya existe un repositorio cargado con el mismo nombre de fichero.
    0: No se ha podido abrir el fichero repositorio.
    1: El fichero ha sido cargado y ha sido ordenado por el campo correspondiente. */
    @Override
    public int AbrirRepositorio(int pIda, String pNomFichero) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Guarda a fichero correspondiente, el repositorio cargado en la biblioteca en la posición que indica
    pRepo. Esta posición se ha de pedir al usuario y en caso de ser -1 se guardarán todos los repositorios
    cargados en la biblioteca en su fichero correspondiente. Las salidas del servicio son:
    -1: Ya hay un usuario identificado como administrador o el Ida no coincide con el almacenado en el
     Servidor.
    -2: El repositorio cuya posición se indica no existe.
    0: No se ha podido guardar a fichero el/los repositorios.
    1: El/los repositorios han sido guardados a sus ficheros correspondientes. */
    @Override
    public int GuardarRepositorio(int pIda, int pRepo) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Guarda el libro L pasado al servicio en el repositorio indicada por pRepo que no podrá ser -1. Una vez
    añadido el libro se deberá ordenar los libros del repositorio por el campo por defecto almacenado en el
    servidor. Las salida del servicio son:
    -1: Ya hay un usuario identificado como administrador o el Ida no coincide con el almacenado en el
 Servidor.
    -2: El repositorio cuya posición se indica no existe.
    0: Hay un libro en algún repositorio de la biblioteca que tiene el mismo Isbn.
    1: Se ha añadido el nuevo libro al repositorio indicado por pRepo. */
    @Override
    public int NuevoLibro(int pIda, TLibro L, int pRepo) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Añadirá un número de ejemplares al libro cuyo Isbn coincida con el pasado por parámetro. Para ello
    buscará el Isbn en todos los repositorios almacenados en la biblioteca. Una vez encontrado, añadirá las
    unidades compradas indicadas por pNoLibros teniendo en cuenta que, si hay más unidades disponibles que
    usuarios en espera, todos estos usuarios recibirán un libro reduciendo la cantidad de libros disponibles,
    y si hay menos libros disponibles que usuarios en espera, se reducirá dicho usuarios en espera en la misma
    cantidad que libros disponibles. Una vez actualizado el libro se ordenarán todos los libros del
    repositorio que contenga el libro. Las salidas de este servicio son:
    -1: Ya hay un usuario identificado como administrador o el Ida no coincide con el almacenado en el
     Servidor.
    0: No se ha encontrado ningún Libro con el Isbn indicado por parámetro.
    1: Se han agregado los nuevos ejemplares del libro y los datos están ordenados. */
    @Override
    public int Comprar(int pIda, String pIsbn, int pNoLibros) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Eliminará un número de ejemplares al libro cuyo Isbn pasado por parámetro. Para ello buscará el Isbn en
    todos los repositorios almacenados en la biblioteca. Una vez encontrado, restará el número de unidades
    indicadas por pNoLibros siempre y cuando haya suficientes. Una vez actualizado el libro, se ordenarán
    todos los libros del repositorio que contenga el libro. Las salidas de este servicio son:
    -1: Ya hay un usuario identificado como administrador o el Ida no coincide con el almacenado en el
    Servidor.
    0: No se ha encontrado ningún Libro con el Isbn indicado por parámetro.
    1: Se han reducido el número de ejemplares disponibles y se han ordenado los datos.
    2: No hay suficientes ejemplares disponibles para ser retirados. */
    @Override
    public int Retirar(int pIda, String pIsbn, int pNoLibros) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Realizará una ordenación de los libros almacenados en todos los repositorios almacenados en la
    biblioteca por el campo indicado por parámetro. El campo indicado se almacenará en el servidor para
    futuras ordenaciones. Las salidas de este servicio son:
    false: Ya hay un usuario identificado como administrador o el Ida no coincide con el almacenado en
     Servidor.
    true: Se ha Ordenado correctamente los repositorios. */
    @Override
    public boolean Ordenar(int pIda, int pCampo) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Devuelve siempre el número de libros del repositorio indicado por parámetro pRepo. Si pRepo es -1
    devolverá la suma de todos los libros de todos los repositorios almacenados en la biblioteca. Las salidas
    de este servicio son:
    -1: Si no existe el repositorio en el servidor y ni tampoco es tampoco el parámetro pRepo es -1.
    >=0: El número de l*/
    @Override
    public int NLibros(int pRepo) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Devuelve la posición del Libro cuya Isbn coincide con el pasado por parámetro. Las salidas de este
    servicio son:
    -2: Ya hay un usuario identificado como administrador o el Ida no coincide con el almacenado en
     Servidor.
    -1: No se ha encontrado ningún Libro con el Isbn indicado por parámetro.
     >=0: La posición del libro dentro de la “mezcla ordenada” de todos los libros de todos los
     repositorios almacenados en la biblioteca. */
    @Override
    public int Buscar(int pIda, String pIsbn) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Devuelve el libro que está en la posición pPos del repositorio está en la posición pRepo. En caso de que
    el campo pRepo sea -1, devolverá el libro de la posición de la “mezcla ordenada” de todos los
    repositorios. En el caso que el Ida no coincida con el almacenado en el servidor, sea pondrá a 0 los
    campos ‘NoPresentados’ y ‘NoListaEspera’ del Libro a devolver. Las salidas del servicio es la siguiente:
     null: En el caso que la posición del repositorio sea incorrecta o la posición del libro sea
     incorrecta.
    ¡null: La referencia a un objeto TLibro. */
    @Override
    public TLibro Descargar(int pIda, int pRepo, int pPos) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Presta a un usuario de la biblioteca un libro cuya posición es indicada por parámetro. Este método
    accede a los libros como si solo hubiera una “mezcla ordenada” de todos los repositorios. Una vez
    localizado el libro, si hay ejemplares disponibles se reducirán en una unidad y los prestados aumentarán
    en una unidad. Si no hubiera ejemplares disponibles se aumentará el número de usuarios en la lista de
    espera. Una vez actualizado el libro se ordenará nuevamente el repositorio que contiene el libro por el
    campo de ordenación almacenado en el servidor. Las salidas de este servicio son:
    -1: La posición indicada no está dentro de los límites del repositorio mezclado y ordenado.
    1: Se ha prestado el libro el libro correctamente.
    0: Se ha puesto el usuario en la lista de espera. */
    @Override
    public int Prestar(int pPos) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*Presta a un usuario de la biblioteca un libro cuya posición es indicada por parámetro. Este método
    accede a los libros como si solo hubiera una “mezcla ordenada” de todos los repositorios. Una vez
    localizado el libro, si hay ejemplares disponibles se reducirán en una unidad y los prestados aumentarán
    en una unidad. Si no hubiera ejemplares disponibles se aumentará el número de usuarios en la lista de
    espera. Una vez actualizado el libro se ordenará nuevamente el repositorio que contiene el libro por el
    campo de ordenación almacenado en el servidor. Las salidas de este servicio son:
    -1: La posición indicada no está dentro de los límites del repositorio mezclado y ordenado.
    1: Se ha prestado el libro el libro correctamente.
    0: Se ha puesto el usuario en la lista de espera. */
    @Override
    public int Devolver(int pPos) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
