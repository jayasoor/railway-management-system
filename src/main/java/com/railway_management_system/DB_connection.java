/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.railway_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Shamika Tissera
 */
public class DB_connection {
    public DB_connection(){
        establishConnection();
    }
    private Statement establishConnection(){
        
        String URL = "jdbc:mysql://localhost:3306/railway_management_system";
        String password = "";
        String user = "root";
        Statement statement;
        statement = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, user, password);
            statement = connection.createStatement();
            
        }
        catch(SQLException e){
            System.err.println("Error when establishing connection... " + e);
        }
        catch(ClassNotFoundException e){
            System.err.println("Problem when fetching JDBC Driver " + e);
        }
        return statement;
    }
    
    protected boolean registerAdmin(String id_no, String password, String uname, String lname, 
            String fname, String email){
        String tableRef = "insert into `administrator` (`id_no`, `password`, `f_name`, `l_name`, `email`)";
        String values = " values (\"" + id_no + "\", \"" + password + "\", " + "\"" 
                + fname + "\", \"" + lname + "\", \"" + email + "\" );";
        
        String tableRef_1 = "insert into `administrator_id_username`(`id_no`, `username`, `administrator_id_no`)";
        String values_2 = " values (\"" + id_no + "\", \"" + uname + "\", \"" + id_no + "\");";
        
        try{
            Statement statement = establishConnection();
            System.err.println(tableRef + values);
            statement.executeUpdate(tableRef + values);
            System.err.println(tableRef_1 + values_2);
            statement.executeUpdate(tableRef_1 + values_2);
            return false;
        }
        catch(SQLException e){
            System.err.println("Administrator information storage error..." + e.getMessage());
            String feedback = e.getMessage().substring(0, 16);
            System.err.println(feedback);
            return true;
        }
    }
    
    protected boolean registerPassenger(String fname, String lname, String mobNo, String id, String email, 
            String uname, String password, boolean isPriority, double credit ){
        String tableRef = "insert into `railway_management_system`.`passenger`(`username`, `password`, "
                + "`email`, `mobile_no`, `credit_available`, `isPriorityPassenger`, `f_name`, `l_name`)";
        String values = "values (\"" + uname + "\", \"" + password + "\", \"" + email + "\", \"" + mobNo 
                + "\", " + credit +  ", " + isPriority + ", \"" + fname + "\", \"" + lname + "\");";
        
        String tableRef_1 = "insert into `passengerid-username`(`id_no`, `username`)";
        String values_2 = " values (\"" + id + "\", \"" + uname + "\");";
        
        try{
            Statement statement = establishConnection();
            System.err.println(tableRef + values);
            statement.executeUpdate(tableRef + values);
            System.err.println(tableRef_1 + values_2);
            statement.executeUpdate(tableRef_1 + values_2);
            return false;
        }
        catch(SQLException e){
            System.err.println("Passenger information storage error..." + e.getMessage());
            String feedback = e.getMessage().substring(0, 16);
            System.err.println(feedback);
            return true;
        }
        
    }
    
    public boolean checkUsername(String username, String table){
        String query = "select `username` from " + table + " where `username` = \"" + username + "\"";
        try{
            Statement statement = establishConnection();
            System.out.println(query);
            ResultSet result = statement.executeQuery(query);
            result.next();
            String uname = result.getString("username");
            return true; //means username exists in the database
        }
        catch(SQLException e){
            System.err.println("Non-existent username... " + e.getMessage());
            return false;//means username does not exist in the database
        }
    }
    
    public boolean checkPassword(String password, String table){
        String query = "select `password` from " + table + " where `password` = \"" + password + "\"";
        try{
            Statement statement = establishConnection();
            System.out.println(query);
            ResultSet result = statement.executeQuery(query);
            result.next();
            String pass = result.getString("password");
            if(pass.equals(password)){
                return true;//means password exists in the database and is correct
            }
            else{
                System.err.println("Password incorrect... ");
                return false;//means password exists in the database but is incorrect
            }            
        }
        catch(SQLException e){
            System.err.println("Password incorrect..." + e.getMessage());
            return false;//means password does not exist in the database
        }
    }
    
    //Sajee
    
    class Handle_Route{
        
        public int insert_info(String routeID, String source, String destination, double generalPrice, double priorityPrice, double distance){
            int count = 0;
            try {
                String routeInsertSQLQuery = "INSERT INTO `route`(`route_id`, `source`, `destination`, `general_price`, `priority_price`, `distance`) "
                        + "VALUES(\"" + routeID + "\", \"" + source + "\", \"" + destination + "\", " + generalPrice + ", " + priorityPrice + ", " + distance + " )";
                Statement statement = establishConnection();
                count = statement.executeUpdate(routeInsertSQLQuery);
                
            } catch (SQLException ex) {
                Logger.getLogger(DB_connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            return count;
        }
        
        public int updateRoute(String routeID, String source, String destination, double generalPrice, double priorityPrice, double distance){
            int count = 0;
            try {
                String updateSQLQuery = "UPDATE `route` SET `source` = \"" + source + "\", " + "`destination`= \"" + destination + "\", " + "`general_price`= " + generalPrice + ", `priority_price`= " + priorityPrice + ", `distance`= " + distance + " WHERE `route_id` = \"" + routeID + "\";";
                
                System.out.println(updateSQLQuery);
                
                Statement statement = establishConnection();
                count = statement.executeUpdate(updateSQLQuery);
                
                //PreparedStatement routePST = this.connx.prepareStatement(routeInsertSQLQuery);
            } catch (SQLException ex) {
                Logger.getLogger(DB_connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            return count;
        }
        
        public int deleteRoute(String routeID){
            int count = 0;
            try {
                String deleteSQLQuery = "DELETE FROM `route` WHERE `route_id` = \"" + routeID + "\"";
                System.out.println(deleteSQLQuery);       
                Statement statement = establishConnection();
                count = statement.executeUpdate(deleteSQLQuery);
            } catch (SQLException ex) {
                Logger.getLogger(DB_connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            return count;
            
        }
    }
    
    class Handle_Train{
        
    }
    
    class Make_Reservation{
        General_Ticket ticket;
        String route_id = null;
        String train_id = null;
        String timetable_id = null;
        int capacity = 0;
        double price;
        
        public General_Ticket getTicket(){
            return ticket;
        }
        
        private String getID(String username){
            try {
                String query = "SELECT `id_no` FROM `passengerid-username` WHERE `username` = \"" + username + "\"";
                Statement statement = establishConnection();
                ResultSet rs = statement.executeQuery(query);
                rs.next();
                return rs.getString("id_no");
            } catch (SQLException ex) {
                Logger.getLogger(DB_connection.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        
        class Reservation_Info{
            String route_ID;
            String g_price;
        }
        
        public boolean createTuple(String username, String date, int count, String source, String destination, Calendar departure){
            try {
                String info_from_route_query = "SELECT general_price, route_id FROM railway_management_system.route where `source` = \"" + source + "\" and `destination` = \"" + destination + "\";";
                
                
                
                
                Statement statement = establishConnection();
                //get general price and route id
                System.out.println(info_from_route_query);
                ResultSet result = statement.executeQuery(info_from_route_query);
                result.next();
                price = result.getDouble("general_price");
                System.out.println(price);
                route_id = result.getString("route_id");
                System.out.println(route_id);
                
                //get train id
                String info_from_timetable_query = "SELECT DISTINCT train_id, time_slot_id FROM timetable where route_route_id = \"" + route_id + "\"";
                System.out.println(info_from_timetable_query);
                result = statement.executeQuery(info_from_timetable_query);
                result.next();
                train_id = result.getString("train_id");
                timetable_id = result.getString("time_slot_id");
                
                //get total capacity
                String info_from_train_query = "SELECT total_capacity FROM train where train_id = \"" + train_id + "\"";
                System.out.println(info_from_train_query);
                result = statement.executeQuery(info_from_train_query);
                result.next();
                capacity = result.getInt("total_capacity");
                String update_reservation_query = "INSERT INTO `reservation` (`date`, `capacity`, `bookings`, `timetable_time_slot_id`, `timetable_train_id`, `timetable_train_train_id`, `timetable_route_route_id`) VALUES ('" + date + "', '" + capacity + "', '" + count + "', '" + timetable_id + "', NULL, NULL, NULL);";
                //update timetable_time_slot_id
                System.out.println(update_reservation_query);
                statement.executeUpdate(update_reservation_query);
                return true; //operation successful
            } catch (SQLException ex) {
                Logger.getLogger(DB_connection.class.getName()).log(Level.SEVERE, null, ex);
                return false; //operation not successful
            }
        }
        
                
                public boolean reserve(String username, String date, int count, String source, String destination, Calendar departure){
            
                String check_query = "select count(*) as \"total\" from reservation where date = \"" + date + "\";";
                String create_query = "insert into reservation(date, bookings) values (\"" + date + "\", " + count + ");";
                String update_query = "update reservation set `bookings` = `bookings` + " + count + " where date = \"" + date + "\";";
                      
                
                
                try {
                    Statement statement = establishConnection();
                    //check whether there are any reservations existing for the given date
                    System.out.println(check_query);
                    ResultSet rs = statement.executeQuery(check_query);
                    rs.next();
                    int rows = rs.getInt("total");
                    System.out.println(rows);
                    if (rows == 0) {
                        boolean status = createTuple(username, date, count, source, destination, departure);
                        if (status) {
                            String id_no = getID(username);
                            General_Ticket ticket = new General_Ticket(id_no, route_id, date, source, destination, price);
                        }
                        return status;
                    }
                    else{
                        String get_capacity_and_count = "SELECT capacity, bookings FROM reservation WHERE date = \"" + date + "\"";
                        rs = statement.executeQuery(get_capacity_and_count);
                        rs.next();
                        int count_temp = rs.getInt("bookings");
                        
                        int capacity_temp = rs.getInt("capacity");
                        
                        if ((count_temp + count) > capacity_temp) {
                            System.out.println("count_temp = " + count_temp);
                            System.out.println("capacity_temp = " + capacity_temp);
                            System.out.println("count = " + count);
                            return false;
                        }
                        
                        System.out.println(update_query);
                        statement.executeUpdate(update_query);
                        String id_no = getID(username);
                        ticket = new General_Ticket(id_no, route_id, date, source, destination, price);
                        return true;
                    }
                    
                } catch (SQLException ex) {
                    System.err.println("Couldn't check query");
                    System.err.println(ex);
                    return false;
                }
                            
        }
        
    }
}
