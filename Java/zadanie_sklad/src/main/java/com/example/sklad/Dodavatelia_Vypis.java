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

@WebServlet(name = "Dodavatelia_Vypis")
public class Dodavatelia_Vypis extends HttpServlet {
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
        out.println("<head><link rel='stylesheet' href='style.css'></head>");
        String operacia = request.getParameter("operacia");
        if (operacia == null) operacia="";
        //out.println("operacia: " + operacia);
        if (operacia.equals("mazanie")) {
            vymazPolozku(out, request.getParameter("id"));
        }

        if (operacia.equals("pridanie")) {
            pridajPolozku(out, request.getParameter("nazov"), request.getParameter("ico"),request.getParameter("adresa"));
        }
        if (operacia.equals("uprava")) {
            upravPolozku(out, request.getParameter("id"), request.getParameter("nazov"), request.getParameter("ico"),
                    request.getParameter("adresa"));
        }
        vypisDatabazy(out);
        if (operacia.equals("zobrForm")) {
            zobrazFormularPrePridanie(out);
        }
        if (operacia.equals("formUprava")) {
            formularUprava(out, request.getParameter("id"), request.getParameter("nazov"), request.getParameter("ico"),
                    request.getParameter("adresa"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private void vypisDatabazy(PrintWriter out) {
        out.println("<form action='Sklad_Vypis' method='post'>");
        out.println("<input type='submit' value='<- Späť'>");
        out.println("</form><br>");
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM dodavatelia");
            out.println("<table style='border-collapse: separate; border-spacing:10px'>");
            out.println("<th></th><th></th><th>Názov dodávateľa</th><th>IČO</th><th>Adresa dodávateľa</th>");
            while (rs.next()) {
                out.println("<tr><td><form style='display:inline' action='Dodavatelia_Vypis' method='post'>");
                out.println("<input type=hidden name ='id' value='" + rs.getString("idDodavatelia") + "'>");
                out.println("<input type=hidden name='operacia' value='formUprava'>");
                out.println("<input type=hidden name ='nazov' value='" + rs.getString("nazov") + "'>");
                out.println("<input type=hidden name ='ico' value='" + rs.getString("ico") + "'>");
                out.println("<input type=hidden name ='adresa' value='" + rs.getString("adresa") + "'>");
                out.println("<input type=submit value='Upraviť'>");
                out.println("</form></td>");
                out.println("<td><form style='display:inline' action='Dodavatelia_Vypis' method='post'>");
                out.println("<input type=hidden name ='id' value='" + rs.getString("idDodavatelia") + "'>");
                out.println("<input type=hidden name='operacia' value='mazanie'>");
                out.println("<input type=submit value='Vymazať'>");
                out.println("</form></td>");
                out.println("<td><p>" + rs.getString("nazov") + "</p></td>");
                out.println("<td><p>" + rs.getString("ico") + "</p></td>");
                out.println("<td><p>" + rs.getString("adresa") + "</p></td></tr>");
            }
            out.println("</table>");
            out.println("<br><br><br><form action='Dodavatelia_Vypis' method='post'>");
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
            String sql = "DELETE FROM dodavatelia WHERE " + id + " = dodavatelia.idDodavatelia";
            int rs = stmt.executeUpdate(sql);
        } catch(Exception e) {
            out.println("<div class=\"alert\">\n" +
                    "  <span class=\"closebtn\" onclick=\"this.parentElement.style.display='none';\">&times;</span>\n" +
                    "  Jedna alebo viac položiek v tabuľke Tovar obsahuje tohto dodávateľa.\n" +
                    "</div>");
        }
    }

    private void pridajPolozku(PrintWriter out, String nazov, String ico, String adresa) {
        try {
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO dodavatelia (nazov, ico, adresa) VALUES (";
            sql += "'" + nazov + "', ";
            sql += "'" + ico + "', ";
            sql += "'" + adresa + "') ";
            int rs = stmt.executeUpdate(sql);
        } catch(Exception e) {
            out.println(e.getMessage());
        }
    }

    private void upravPolozku(PrintWriter out, String id, String nazov, String ico, String adresa) {
        try {
            Statement stmt = con.createStatement();
            String sql = "UPDATE dodavatelia SET nazov = '" + nazov + "', ico = " + ico + ", adresa = '" + adresa + "' WHERE idDodavatelia = " + id;
            int rs = stmt.executeUpdate(sql);
        } catch(Exception e) {
            out.println(e.getMessage());
        }
    }

    private void zobrazFormularPrePridanie(PrintWriter out) {
        out.println("<form action='Dodavatelia_Vypis' method='post'>");
        out.println("<label style='display:table-row' for='nazov'>Názov dodávateľa:</label>");
        out.println("<input type='text' name='nazov'><br>");
        out.println("<label style='display:table-row' for='ico'>IČO:</label>");
        out.println("<input type='number' name='ico'><br>");
        out.println("<label style='display:table-row' for='adresa'>Adresa dodávateľa:</label>");
        out.println("<input type='text' name='adresa'>");
        out.println("<input type='hidden' name='operacia' value='pridanie'>");
        out.println("<input type='submit' value='Vlož'>&emsp;");
        out.println("</form>");

        out.println("<form action='Dodavatelia_Vypis' method='post'>");
        out.println("<input type='submit' value='Zatvor'");
        out.println("</input>");
    }

    private void formularUprava(PrintWriter out, String id, String nazov, String ico, String adresa) {
        try {
            out.println("<form action='Dodavatelia_Vypis' method='post'>");
            out.println("<label style='display:table-row' for='nazov'>Názov dodávateľa:</label>");
            out.println("<input type='text' name='nazov' value='" + nazov + "'><br>");
            out.println("<label style='display:table-row' for='ico'>IČO:</label>");
            out.println("<input type='number' name='ico' value='" + ico + "'><br>");
            out.println("<label style='display:table-row' for='adresa'>Adresa dodávateľa:</label>");
            out.println("<input type='text' name='adresa' value='" + adresa + "'>");
            out.println("<input type='hidden' name='operacia' value='uprava'>");
            out.println("<input type='hidden' name='id' value='" + id + "'>");
            out.println("<input type='submit' value='Upraviť'>&emsp;");
            out.println("</form>");

            out.println("<form action='Dodavatelia_Vypis' method='post'>");
            out.println("<input type='submit' value='Zatvor'");
            out.println("</input>");
        } catch(Exception e) {
            out.println(e.getMessage());
        }
    }
}
