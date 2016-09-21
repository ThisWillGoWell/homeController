package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Willi on 9/20/2016.
 */
public class SqlController {
    private final String SQL_SERVER_ADDRESS = "http://localhost:3036";

    Connection conn = null;
    Statement st = null;
    String url = "http://localhost:3036";

    String user = "homeController";
    String password = "password23";


}
