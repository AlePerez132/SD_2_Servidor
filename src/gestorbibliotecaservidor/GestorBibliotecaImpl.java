/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorbibliotecaservidor;

import gestorbibliotecacomun.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author alepd
 */
//IMPORTANTE: SE CONSIDERA QUE CUANDO SE PASA LA POSICION DE UN REPOSITORIO POR PARAMETRO, SE PASARA UN NUMERO DE 1 A N,
//POR TANTO, DEBEMOS RESTARLE 1 EN EL SERVIDOR PARA CONVERTIRLO EN UN RANGO DE 0 A N-1
public class GestorBibliotecaImpl implements GestorBibliotecaIntf {

    private int nRepo = 0;
    private int idAdmin = -1;
    private int campoOrdenacion = 0;
    private ArrayList<Repositorio> repositorios = new ArrayList<>();
    private ArrayList<TLibro> Libros = new ArrayList<>();
    private String nombreFichero = "";
    private final Comparator<TLibro> c = new Comparator<>() {
        @Override
        public int compare(TLibro o1, TLibro o2) {
            int C = 0;
            switch (campoOrdenacion) {
                case 0:
                    C = o1.getIsbn().compareTo(o2.getIsbn());
                    break;
                case 1:
                    C = o1.getTitulo().compareTo(o2.getTitulo());
                    break;
                case 2:
                    C = o1.getAutor().compareTo(o2.getAutor());
                    break;
                case 3:
                    C = Integer.compare(o1.getAnio(), o2.getAnio());
                    break;
                case 4:
                    C = o1.getPais().compareTo(o2.getPais());
                    break;
                case 5:
                    C = o1.getPais().compareTo(o2.getPais());
                    break;
                case 6:
                    C = Integer.compare(o1.getNoLibros(), o2.getNoLibros());
                    break;
                case 7:
                    C = Integer.compare(o1.getNoPrestados(), o2.getNoPrestados());
                    break;
                case 8:
                    C = Integer.compare(o1.getNoListaEspera(), o2.getNoListaEspera());
                    break;
            }
            return C;
        }
    };

    /*Se encarga de verificar la contraseña de administrador y devolverá un número (IDA) dependiendo de las
    siguientes condiciones:
    -1: Ya hay un usuario identificado como administrador.
    -2: La contraseña es errónea.
    N: Un número aleatorio generado en el intervalo [1, 1000000].
    Este número deberá ser utilizado en todas las operaciones de Administración en el campo Ida. */
    @Override
    public int Conexion(String pPasswd) throws RemoteException {
        if (idAdmin != -1) {
            return -1;
        } else {
            if (!pPasswd.equals("1234")) {
                return -2;
            } else {
                Random r = new Random(System.nanoTime());
                idAdmin = r.nextInt(1000000) + 1;
                return idAdmin;
            }
        }
    }

    /*Comprueba que el Ida coincide con el almacenado en el servidor. Si no coincide devuelve false y caso
    contrario borra el Ida (lo pone a -1) almacenado en el servidor y devuelve true. */
    @Override
    public boolean Desconexion(int pIda) throws RemoteException {
        if (pIda != idAdmin) {
            return false;
        } else {
            idAdmin = -1;
            return true;
        }
    }

    /*Devuelve el número de repositorios almacenados en la biblioteca. En caso de que exista otro usuario
    identificado como administrador devolverá -1. */
    @Override
    public int NRepositorios(int pIda) throws RemoteException {
        if (pIda != idAdmin) {
            return -1;
        } else {
            return nRepo;
        }
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
        if (pIda != idAdmin) {
            return null;
        } else {
            if (pPosRepo < 1 || pPosRepo > nRepo) { //asumo que el dato que se pasa va desde 1 hasta n
                return null;
            } else {
                return repositorios.get(pPosRepo - 1).getDatos(); //por eso le pongo el -1
            }
        }
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
        if (pIda != idAdmin) {
            return -1;
        } else {
            try ( DataInputStream dis = new DataInputStream(new FileInputStream(pNomFichero))) { //abrir el fichero
                nombreFichero = pNomFichero;
                int numLibros = dis.readInt();
                String nombreRepositorio = dis.readUTF();
                String direccionRepositorio = dis.readUTF(); //leemos datos del repositorio

                int j = 0;
                while (j < repositorios.size()) {
                    if (repositorios.get(j).getDatos().getNombre().equals(nombreRepositorio)) { //busco que no haya un repositorio con el mismo nombre
                        return -2;
                    } else {
                        j++;
                    }
                }
                //no hay ningun repositorio con el mismo nombre, procedemos a guardar los libros
                ArrayList<TLibro> libros = new ArrayList<>();
                for (int i = 0; i < numLibros; i++) {
                    String isbn = dis.readUTF();
                    String titulo = dis.readUTF();
                    String autor = dis.readUTF();
                    int anio = dis.readInt();
                    String pais = dis.readUTF();
                    String idioma = dis.readUTF();
                    int noLibros = dis.readInt();
                    int noPrestados = dis.readInt();
                    int noListaEspera = dis.readInt();

                    //crear objeto TLibro y añadirlo a la lista
                    TLibro libro = new TLibro(isbn, titulo, autor, anio, pais, idioma, noLibros, noPrestados, noListaEspera);
                    libros.add(libro); //array auxiliar para meterlo en el repositorio
                    Libros.add(libro); //array que contiene todos los libros de todos los repositorios;
                }

                //tenemos todo el dato de los repositorios, solo falta crearlo y anadirlo
                TDatosRepositorio datos = new TDatosRepositorio(nombreRepositorio, direccionRepositorio, numLibros);
                Repositorio repositorio = new Repositorio(libros, datos);
                repositorios.add(repositorio);
                nRepo++;
                Ordenar(idAdmin, campoOrdenacion);
                return 1;
            } catch (IOException e) {
                return 0; //si el fichero no se ha abierto correctamente
            }

        }
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
        if (pIda != idAdmin) {
            return -1;
        } else if (nombreFichero.equals("")) {
            return 0;
        } else if (pRepo == -1) {
            for (Repositorio repo : repositorios) {
                try ( DataOutputStream output = new DataOutputStream(new FileOutputStream(nombreFichero))) {
                    output.writeInt(repo.getDatos().getNumLibros());
                    output.writeUTF(repo.getDatos().getNombre());
                    output.writeUTF(repo.getDatos().getDireccion());

                    for (TLibro libro : repo.getLibros()) {
                        output.writeUTF(libro.getIsbn());
                        output.writeUTF(libro.getTitulo());
                        output.writeUTF(libro.getAutor());
                        output.writeInt(libro.getAnio());
                        output.writeUTF(libro.getPais());
                        output.writeUTF(libro.getIdioma());
                        output.writeInt(libro.getNoLibros());
                        output.writeInt(libro.getNoPrestados());
                        output.writeInt(libro.getNoListaEspera());
                    }
                    return 1;
                } catch (IOException e) {
                    return 0;
                }
            }
        } else if (pRepo < 1 || pRepo > repositorios.size()) {
            return -2;
        } else {
            Repositorio repo = repositorios.get(pRepo - 1);
            try ( DataOutputStream output = new DataOutputStream(new FileOutputStream(nombreFichero))) {
                output.writeInt(repo.getDatos().getNumLibros());
                output.writeUTF(repo.getDatos().getNombre());
                output.writeUTF(repo.getDatos().getDireccion());

                for (TLibro libro : repo.getLibros()) {
                    output.writeUTF(libro.getIsbn());
                    output.writeUTF(libro.getTitulo());
                    output.writeUTF(libro.getAutor());
                    output.writeInt(libro.getAnio());
                    output.writeUTF(libro.getPais());
                    output.writeUTF(libro.getIdioma());
                    output.writeInt(libro.getNoLibros());
                    output.writeInt(libro.getNoPrestados());
                    output.writeInt(libro.getNoListaEspera());
                }
                return 1;
            } catch (IOException e) {
                return 0;
            }
        }
        return -2;
        //EL UNICO CASO EN EL QUE SE PUEDE METER AQUI ES SI EN LA LINEA for (Repositorio repo : repositorios) { LA LISTA DE
        //REPOSITORIOS ESTA VACIA, POR ESO LE DIGO QUE LA POSICION DEL REPOSITORIO ES ERRONEA
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
        System.out.println("Isbn del libro " + L.getIsbn());
        System.out.println("Autor del libro " + L.getAutor());
        if (pIda != idAdmin) {
            return -1;
        } else if (pRepo < 1 || pRepo > repositorios.size()) {
            return -2;
        } else {
            int i = 0;
            while (i < Libros.size()) {
                if (repositorios.get(pRepo - 1).getLibros().get(i).getIsbn().equals(L.getIsbn())) {
                    return 0;
                } else {
                    i++;
                }
            }
            repositorios.get(pRepo - 1).getLibros().add(L);
            Ordenar(idAdmin, campoOrdenacion);
            return 1;
        }
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
        if (pIda != idAdmin) {
            return -1;
        } else {
            int i = 0;
            boolean encontrado = false;
            while (!encontrado || i < Libros.size()) {
                if (Libros.get(pNoLibros).getIsbn().equals(pIsbn)) {
                    encontrado = true;
                } else {
                    i++;
                }
            }
            if (!encontrado) {
                return 0;
            } else {
                while (pNoLibros > 0 && Libros.get(i).getNoListaEspera() > 0) {
                    Libros.get(i).setNoListaEspera(Libros.get(i).getNoListaEspera() - 1); //Libros.get(i).NoListaEspera--
                    pNoLibros--;
                }
                Libros.get(i).setNoLibros(Libros.get(i).getNoLibros() + pNoLibros); //Libros.get(i).NoLibros += librosComprados
                Ordenar(idAdmin, campoOrdenacion);
                return 1;
            }
        }
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
        if (pIda != idAdmin) {
            return -1;
        } else {
            int i = 0;
            boolean encontrado = false;
            while (!encontrado || i < Libros.size()) {
                if (Libros.get(pNoLibros).getIsbn().equals(pIsbn)) {
                    encontrado = true;
                } else {
                    i++;
                }
            }
            if (!encontrado) {
                return 0;
            } else {
                if (pNoLibros > Libros.get(i).getNoLibros()) {
                    return -2;
                } else {
                    Libros.get(i).setNoLibros(Libros.get(i).getNoLibros() - pNoLibros); //Libros.get(i).NoLibros-=pNoLibros
                    Ordenar(idAdmin, campoOrdenacion);
                    return 1;
                }
            }
        }
    }

    /*Realizará una ordenación de los libros almacenados en todos los repositorios almacenados en la
    biblioteca por el campo indicado por parámetro. El campo indicado se almacenará en el servidor para
    futuras ordenaciones. Las salidas de este servicio son:
    false: Ya hay un usuario identificado como administrador o el Ida no coincide con el almacenado en
     Servidor.
    true: Se ha Ordenado correctamente los repositorios. */
    @Override
    public boolean Ordenar(int pIda, int pCampo) throws RemoteException {
        if (pIda != idAdmin) {
            return false;
        } else {
            campoOrdenacion = pCampo;
            Libros.sort(c);
            for (Repositorio repo : repositorios) {
                repo.getLibros().sort(c);
            }
            return true;
        }
    }

    /*Devuelve siempre el número de libros del repositorio indicado por parámetro pRepo. Si pRepo es -1
    devolverá la suma de todos los libros de todos los repositorios almacenados en la biblioteca. Las salidas
    de este servicio son:
    -1: Si no existe el repositorio en el servidor y ni tampoco es tampoco el parámetro pRepo es -1.
    >=0: El número de l*/
    @Override
    public int NLibros(int pRepo) throws RemoteException {
        if (pRepo == -1) {
            int numLibrosTotal = 0;
            for (Repositorio repo : repositorios) {
                numLibrosTotal += repo.getDatos().getNumLibros();
            }
            return numLibrosTotal;
        } else {
            if (pRepo > repositorios.size()) {
                return -1;
            } else {
                return repositorios.get(pRepo - 1).getDatos().getNumLibros(); //asumimos que el parametro va de 1 a N
            }
        }
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
        if (pIda != idAdmin) {
            return -2;
        } else {
            int i = 0;
            while (i < Libros.size()) {
                if (Libros.get(i).getIsbn().equals(pIsbn)) {
                    return i; //se devuelven en un rango de 0 a N-1
                } else {
                    i++;
                }
            }
            return -1;
        }
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
        if (pRepo == -1) {
            if (pPos < 1 || pPos > Libros.size()) {
                return null;
            } else {
                return Libros.get(pPos - 1);
            }
        } else if (pRepo < 1 || pRepo > nRepo) {
            return null;
        } else {
            Repositorio repo = repositorios.get(pRepo - 1);
            if (pPos < 1 || pPos > repo.getLibros().size()) {
                return null;
            } else {
                return repo.getLibros().get(pPos - 1);
            }
        }
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
        if (pPos < 1 || pPos > Libros.size()) {
            return -1;
        } else if (Libros.get(pPos).getNoLibros() <= 0) { //si no hay libros disponibles
            Libros.get(pPos).setNoListaEspera(Libros.get(pPos).getNoListaEspera() + 1); //Libros.get(i).NoListaEspera++
            return 0;
        } else {
            Libros.get(pPos).setNoLibros(Libros.get(pPos).getNoLibros() - 1);  //Libros.get(i).NoListaEspera--
            Libros.get(pPos).setNoPrestados(Libros.get(pPos).getNoPrestados() + 1); //Libros.get(i).NoPrestados++
            Ordenar(idAdmin, campoOrdenacion);
            return 1;
        }
    }

    /*Devuelve un ejemplar del libro cuya posición es indicada por parámetro. Este método accede a los libros
    como si solo hubiera una “mezcla ordenada” de todos los repositorios. Una vez localizado el libro, si hay
    usuarios en espera se reducirá en una unidad y Si no hay usuarios en espera pero si libros prestados, se
    reducirá el número de libros prestados y se aumentará el número de libros disponibles. Una vez actualizado
    el libro se ordenará nuevamente el repositorio que contiene el libro por el campo de ordenación almacenado
    en el servidor. Las salidas de este servicio son
    -1: La posición indicada no está dentro de los límites del repositorio mezclado y ordenado.
    0: Se ha devuelto el libro reduciendo el número de usuarios en espera.
    1: Se ha devuelto aumentando el número de libros disponibles.
    2: El libro no se puede devolver, porque no hay ni usuarios en lista de espera ni libros
     prestados. */
    @Override
    public int Devolver(int pPos) throws RemoteException {
        if (pPos < 1 || pPos > Libros.size()) {
            return -1;
        } else if (Libros.get(pPos).getNoListaEspera() > 0) { //si hay libros disponibles
            Libros.get(pPos).setNoListaEspera(Libros.get(pPos).getNoListaEspera() - 1); //Libros.get(i).NoLibros++
            Ordenar(idAdmin, campoOrdenacion);
            return 0;
        } else if (Libros.get(pPos).getNoPrestados() > 0) {
            Libros.get(pPos).setNoLibros(Libros.get(pPos).getNoLibros() + 1);  //Libros.get(i).NoListaEspera+
            Libros.get(pPos).setNoPrestados(Libros.get(pPos).getNoPrestados() - 1); //Libros.get(i).NoPrestados--
            Ordenar(idAdmin, campoOrdenacion);
            return 1;
        } else {
            return 2;
        }
    }

}
