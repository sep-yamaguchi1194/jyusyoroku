package jyusyoroku.sep.yt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditCommit
 */
@WebServlet("/EditCommit")
public class EditCommit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCommit() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //doGet(request, response);

        Connection connect = null;
        //Statement stmt = null;
        PreparedStatement ps = null;
        //ResultSet rs = null;
        String UpdQuery = "";
        int id = 0;
        String name = "";
        String address = "";
        String tel = "";
        String categoryid = "";

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        id = Integer.parseInt(request.getParameter("id"));
        name = request.getParameter("name");
        address = request.getParameter("address");
        tel = request.getParameter("tel");
        categoryid = request.getParameter("categoryid");

        //telからハイフンを除去
        tel = tel.replace("-", "");

        try {
            String DATABASE_NAME = "java_mysql";
            String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
            String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME + PROPATIES;
            String USER = "root";
            String PASS = "password";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        UpdQuery = "UPDATE jyusyoroku SET "
                 + "name = ?, "
                 + "address = ?, "
                 + "tel = ?,"
                 + "categoryid = ?"
                 + "WHERE id = ?";

        try {
            ps = connect.prepareStatement(UpdQuery);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, tel);
            ps.setString(4, categoryid);
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        //ListBLへ遷移
        ServletContext context = this.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/ListBL");
        dispatcher.forward(request, response);
    }

}
