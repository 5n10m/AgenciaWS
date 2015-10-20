/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuelo;

import com.sun.org.apache.bcel.internal.util.SecuritySupport;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author riogemm.panopio
 */
@WebService(serviceName = "VueloWS")
public class VueloWS {

    /**
     * Dados un identificador de vuelo y una fecha, retorna el número de plazas que están libres
     */
    
    @WebMethod(operationName = "consulta_libres")
    public int consulta_libres(@WebParam(name = "id_vuelo") int id_vuelo, @WebParam(name = "fecha") int fecha) {
        //TODO write your implementation code here:
        try {
        Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\david.molins.goma\\Downloads\\sqlite-jdbc-3.7.2");
            Statement statement = connection.createStatement();
            String id = Integer.toString(id_vuelo);
            String date = Integer.toString(fecha);
            ResultSet rs = statement.executeQuery("select num_plazas_max, num_plazas_ocupadas from vuelo_fecha where id_vuelo = " + id + " and fecha = " + date);
            int max = rs.getInt("num_plazas_max");
            int ocupadas = rs.getInt("num_plazas_ocupadas");
            int libres = max - ocupadas;
            System.out.println(libres);
            return libres;
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Dados un identificador de vuelo y una fecha, reserva una plaza si quedan plazas libres (incrementa el número de plazas ocupadas en un vuelo en una fecha.
     * Si es posible realizar la reserva, esta operación retorna el número de plazas ocupadas que hay en el vuelo.
     * Si no es posible realizar la reserva, esta operación retorna -1.
     */
    
    @WebMethod(operationName = "reserva_plaza")
    public int reserva_plaza(@WebParam(name = "id_vuelo") int id_vuelo, @WebParam(name = "fecha") int fecha){
        //TODO write your implementation code here:
        try {
        Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Rio\\Dropbox\\UPC\\AD\\P3\\practica3.sqlite");
            Statement statement = connection.createStatement();
            String id = Integer.toString(id_vuelo);
            String date = Integer.toString(fecha);
            ResultSet rs = statement.executeQuery("select num_plazas_max, num_plazas_ocupadas from vuelo_fecha where id_vuelo = " + id + " and fecha = " + date);
            int max = rs.getInt("num_plazas_max");
            int ocupadas = rs.getInt("num_plazas_ocupadas");
            int libres = max - ocupadas;
            System.out.println(libres);
            if (libres > 0) {
                rs = statement.executeQuery("update vuelo_fecha set num_plazas_ocupadas = num_plazas_ocupadas + 1 where id_vuelo = " + id + " and fecha = " + date);
                System.out.println("S'ha reservat correctament. Ocupades: " + libres+1);
                return libres;
            }
            else {
                System.out.println("No hi ha places lliures");
                return -1;
            }
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }
}
