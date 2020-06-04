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
 * Servlet implementation class DeleteCommit
 */
@WebServlet("/DeleteCommit")
public class DeleteCommit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCommit() {
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
        //論理削除用クエリ格納用変数LogicalDelQuery
        String LogicalDelQuery = "";
        int id = 0;

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        id = Integer.parseInt(request.getParameter("id"));

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

        LogicalDelQuery = "UPDATE jyusyoroku SET "
                        + "delete_flg = '1' "
                        + "WHERE id = ?";

        try {
            ps = connect.prepareStatement(LogicalDelQuery);
            ps.setInt(1, id);
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
