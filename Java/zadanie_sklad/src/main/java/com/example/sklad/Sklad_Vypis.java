package com.example.sklad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "Sklad_Vypis")
public class Sklad_Vypis extends HttpServlet {
    Connection con = null;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/sklad", "root", "");
        } catch(Exception e) {}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if(con == null) {
            out.println("Neexistuje pripojenie na databázu");
            return;
        }
        String operacia = request.getParameter("operacia");
        if (operacia == null) operacia="";
        //out.println("operacia: " + operacia);
        if (operacia.equals("mazanie")) {
            vymazPolozku(out, request.getParameter("id"));
        }
        if (operacia.equals("pridanie")) {
            pridajPolozku(out, request.getParameter("nazov"), request.getParameter("cena"), request.getParameter("dodavatel"));
        }
        if (operacia.equals("uprava")) {
            upravPolozku(out, request.getParameter("id"), request.getParameter("nazov"), request.getParameter("cena"),
                    request.getParameter("dodavatel"));
        }
        vypisDatabazy(out);
        if (operacia.equals("zobrForm")) {
            zobrazFormularPrePridanie(out);
        }
        if (operacia.equals("formUprava")) {
            formularUprava(out, request.getParameter("nazov"), request.getParameter("cena"), request.getParameter("dodavatel"),
                    request.getParameter("idTovar"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private void vypisDatabazy(PrintWriter out) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idTovar, tovar.nazov, cena, dodavatelia.nazov AS dodavatel" +
                    ", dodavatelia.idDodavatelia AS idDod FROM tovar INNER JOIN dodavatelia ON tovar.idDodavatelia = dodavatelia.idDodavatelia");
            out.println("<form action='Dodavatelia_Vypis' method='post'>");
            out.println("<input type='submit' value='Dodávatelia'>");
            out.println("</form><br>");
            out.println("<table style='border-collapse: separate; border-spacing:10px'>");
            out.println("<th></th><th></th><th>Názov</th><th>Cena (€)</th><th>Dodávateľ</th>");
            while (rs.next()) {
                out.println("<tr><td><form style='display:inline' action='Sklad_Vypis' method='post'>");
                out.println("<input type=hidden name ='id' value='" + rs.getString("idTovar") + "'>");
                out.println("<input type=hidden name='operacia' value='formUprava'>");
                out.println("<input type=hidden name= 'idTovar' value='" + rs.getString("idTovar") + "'>");
                out.println("<input type=hidden name ='nazov' value='" + rs.getString("nazov") + "'>");
                out.println("<input type=hidden name ='cena' value='" + rs.getString("cena") + "'>");
                out.println("<input type=hidden name ='dodavatel' value='" + rs.getString("idDod") + "'>");
                out.println("<input type=submit value='Upraviť'>");
                out.println("</form></td>");
                out.println("<td><form style='display:inline' action='Sklad_Vypis' method='post'>");
                out.println("<input type=hidden name ='id' value='" + rs.getString("idTovar") + "'>");
                out.println("<input type=hidden name='operacia' value='mazanie'>");
                out.println("<input type=submit value='Vymazať'>");
                out.println("</form></td>");
                out.println("<td><p>" + rs.getString("nazov") + "</p></td>");
                out.println("<td><p>" + rs.getString("cena") + "</p></td>");
                out.println("<td><p>" + rs.getString("dodavatel") + "</p></td></tr>");
            }
            out.println("</table>");
            out.println("<br><br><br><form action='Sklad_Vypis' method='post'>");
            out.println("<input type='hidden' name='operacia' value='zobrForm'>");
            out.println("<input type='submit' value='Vložiť'>");
            out.println("</form><br>");
        } catch(Exception e) {
            out.println(e.getMessage());
        }
    }

    private void vymazPolozku(PrintWriter out, String id) {
        try {
            Statement stmt = con.createStatement();
            String sql = "DELETE FROM tovar WHERE " + id + " = tovar.idTovar";
            int rs = stmt.executeUpdate(sql);
        } catch(Exception e) {
            out.println(e.getMessage());
        }
    }

    private void pridajPolozku(PrintWriter out, String nazov, String cena, String dodavatel) {
        try {
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO tovar (nazov, cena, idDodavatelia) VALUES (";
            sql += "'" + nazov + "', ";
            sql += "'" + cena + "', ";
            sql += "'" + dodavatel + "') ";
            int rs = stmt.executeUpdate(sql);
        } catch(Exception e) {
            out.println(e.getMessage());
        }
    }

    private void upravPolozku(PrintWriter out, String id, String nazov, String cena, String dodavatel) {
        try {
            Statement stmt = con.createStatement();
            String sql = "UPDATE tovar SET nazov = '" + nazov + "', cena = " + cena + ", idDodavatelia = " + dodavatel + " WHERE idTovar = " + id;
            int rs = stmt.executeUpdate(sql);
        } catch(Exception e) {
            out.println(e.getMessage());
        }
    }

    private void zobrazFormularPrePridanie(PrintWriter out) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idDodavatelia, nazov FROM dodavatelia");
            out.println("<form action='Sklad_Vypis' method='post'>");
            out.println("<label style='display:table-row' for='nazov'>Názov tovaru:</label>");
            out.println("<input type='text' name='nazov'><br>");
            out.println("<label style='display:table-row' for='cena'>Cena tovaru:</label>");
            out.println("<input type='number' step='0.01' min='0' name='cena'><br>");
            out.println("<label style='display:table-row' for='dodavatel'>Dodávateľ:</label>");
            out.println("<select name='dodavatel'>");
            while(rs.next())
                out.println("<option value='" + rs.getString("idDodavatelia") + "'>" + rs.getString("nazov") + "</option>");
            out.println("</select>");
            out.println("<input type='hidden' name='operacia' value='pridanie'>");
            out.println("<input type='submit' value='Vlož'>&emsp;");
            out.println("</form>");

            out.println("<form action='Sklad_Vypis' method='post'>");
            out.println("<input type='submit' value='Zatvor'");
            out.println("</input>");
        } catch(Exception e) {
            out.println(e.getMessage());
        }
    }

    private void formularUprava(PrintWriter out, String nazov, String cena, String dodavatel, String idTovaru) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idDodavatelia, nazov FROM dodavatelia");
            out.println("<form action='Sklad_Vypis' method='post'>");
            out.println("<label style='display:table-row' for='nazov'>Názov tovaru:</label>");
            out.println("<input type='text' name='nazov' value='" + nazov + "'><br>");
            out.println("<label style='display:table-row' for='cena'>Cena tovaru:</label>");
            out.println("<input type='number' step='0.01' min='0' name='cena' value='" + cena + "'><br>");
            out.println("<label style='display:table-row' for='dodavatel'>Dodávateľ:</label>");
            out.println("<select name='dodavatel'>");
            while(rs.next()) {
                if(dodavatel.equals(rs.getString("idDodavatelia")))
                    out.println("<option selected value='" + rs.getString("idDodavatelia") + "'>" + rs.getString("nazov") + "</option>");
                else
                    out.println("<option value='" + rs.getString("idDodavatelia") + "'>" + rs.getString("nazov") + "</option>");
            }
            out.println("</select>");
            out.println("<input type='hidden' name='operacia' value='uprava'>");
            out.println("<input type='hidden' name='id' value='" + idTovaru + "'>");
            out.println("<input type='submit' value='Upraviť'>&emsp;");
            out.println("</form>");

            out.println("<form action='Sklad_Vypis' method='post'>");
            out.println("<input type='submit' value='Zatvor'");
            out.println("</input>");
        } catch(Exception e) {
            out.println(e.getMessage());
        }
    }

}
