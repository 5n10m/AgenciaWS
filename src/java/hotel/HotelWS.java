/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

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
@WebService(serviceName = "HotelWS")
public class HotelWS {
    String dbpath = "\"jdbc:sqlite:C:\\\\Users\\\\david.molins.goma\\\\Downloads\\\\sqlite-jdbc-3.7.2\"";

    @WebMethod(operationName = "consulta_libres")
    public int consulta_libres(@WebParam(name = "id_hotel") int id_hotel, @WebParam(name = "fecha") int fecha){
        //TODO write your implementation code here:
        try {
        Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbpath);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT num_hab_libres FROM hotel_fecha where id_hotel = "+ id_hotel +" and fecha = "+ fecha );
            return Integer.parseInt(rs.getString("num_hab_libres"));
        } catch(SQLException e) {
                System.err.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Dados un identificador de hotel y una fecha, reserva una habitación si quedan habitaciones libres (incrementa el número de habitaciones ocupadas en esa fecha en el hotel).
     * Si es posible realizar la reserva, esta operación retorna el número de habitaciones ocupadas que hay en el hotel.
     * Si no es posible realizar la reserva, esta operación retorna -1.
     */
    
    @WebMethod(operationName = "reserva_habitacion")
    public int reserva_habitacion(@WebParam(name = "id_hotel") int id_hotel, @WebParam(name = "fecha") int fecha){
        //TODO write your implementation code here:
    try {
        Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbpath);
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT num_hab_libres FROM hotel_fecha where id_hotel = "+ id_hotel +" and fecha = "+ fecha);
            if(0 < Integer.parseInt(res.getString("num_hab_libres"))){
                Integer rs = statement.executeUpdate("UPDATE hotel_fecha SET num_hab_libres = num_hab_libres-1, num_hab_ocupadas = num_hab_ocupadas + 1 WHERE id_hotel = "+ id_hotel +" and fecha = "+ fecha);
                //return Integer.parseInt(rs.getString("num_hab_libres"));
                if(rs>0) return rs;
            }
            return -1;
        } catch(SQLException e) {
                System.err.println(e.getMessage());
        }
        return 0;
    }
}
