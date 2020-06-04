package jyusyoroku.sep.yt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListBL
 */
@WebServlet("/ListBL")
public class ListBL extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection connect = null;
    Statement stmt = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int listCnt = 0;
    String SelectQuery = "";
    String CntQuery = "";
    String nowPage = "";
    String SearchName = "";
    int limitSta = 0;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListBL() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //response.getWriter().append("Served at: ").append(request.getContextPath());
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //doGet(request, response);

        request.setCharacterEncoding("UTF-8");

        //リクエストPage
        if(request.getParameter("Page") == null) {
            nowPage = "1";
        } else {
            nowPage = request.getParameter("Page");
        }

        //リクエストSearchName
        if(!(request.getParameter("SearchName") == null)) {
            SearchName = request.getParameter("SearchName");
        }

        limitSta = (Integer.parseInt(nowPage) - 1) * 10;

        try {
            connect = connectDB();
        } catch (SQLException | ClassNotFoundException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
            System.out.println("DB接続失敗");
        }

        //レコード総件数取得用クエリ
        CntQuery = "SELECT COUNT(id) "
                 + "FROM jyusyoroku "
                 + "WHERE delete_flg = '0' ";

        //レコード取得用クエリ
        SelectQuery = "SELECT jyusyoroku.id, "
                        + "jyusyoroku.name, "
                        + "jyusyoroku.address, "
                        + "jyusyoroku.tel, "
                        + "jyusyoroku.categoryid, "
                        + "category.categoryname "
                    + "FROM jyusyoroku "
                    + "INNER JOIN category "
                    + "ON jyusyoroku.categoryid = category.categoryid "
                    + "WHERE jyusyoroku.delete_flg = '0' ";

        //リクエストSearchNameが空文字でなければCntQuery, SelectQueryにそれぞれWHERE句への検索条件追加
        if(!SearchName.isEmpty()) {
            CntQuery += "AND jyusyoroku.address LIKE ? ";
            SelectQuery += "AND jyusyoroku.address LIKE ? ";
        }

        //SelectQueryにLIMIT句を追加
        SelectQuery += "LIMIT " + limitSta + ", " + 10;

        if(!SearchName.isEmpty()) {
            try {
                ps = connect.prepareStatement(CntQuery);
                ps.setString(1, "%" + SearchName + "%");
                rs = ps.executeQuery();
                rs.next();
                listCnt = rs.getInt(1);

                ps = connect.prepareStatement(SelectQuery);
                ps.setString(1, "%" + SearchName + "%");
                rs = ps.executeQuery();
            } catch (SQLException e1) {
                // TODO 自動生成された catch ブロック
                e1.printStackTrace();
            }
        } else {
            try {
                stmt = connect.createStatement();
                rs = stmt.executeQuery(CntQuery);
                rs.next();
                listCnt = rs.getInt(1);

                stmt = connect.createStatement();
                rs = stmt.executeQuery(SelectQuery);
            } catch (SQLException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }

        request.setAttribute("Result", rs);
        request.setAttribute("listCnt", listCnt);
        request.setAttribute("Page", nowPage);
        request.setAttribute("SearchName", SearchName);

        connect = null;

        ServletContext context = this.getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/List.jsp");
        dispatcher.forward(request, response);
    }

    //DB接続用 Connection オブジェクトを返す
    protected Connection connectDB() throws SQLException, ClassNotFoundException {
        String DATABASE_NAME = "java_mysql";
        String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
        String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME + PROPATIES;
        String USER = "root";
        String PASS = "password";
        Connection connect = null;

        //MySQL に接続する
        Class.forName("com.mysql.cj.jdbc.Driver");
        //データベースに接続
        connect = DriverManager.getConnection(URL, USER, PASS);
        return connect;
    }

}
