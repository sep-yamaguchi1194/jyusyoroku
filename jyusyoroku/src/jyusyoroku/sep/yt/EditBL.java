package jyusyoroku.sep.yt;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditBL
 */
@WebServlet("/EditBL")
public class EditBL extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditBL() {
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
        String id = "";
        String name = "";
        String address = "";
        String tel = "";
        String categoryid = "";
        String errmsg = "";

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        //リクエストの値を変数にセット
        id = request.getParameter("id");
        name = request.getParameter("name");
        address = request.getParameter("address");
        tel = request.getParameter("tel");
        categoryid = request.getParameter("categoryid");

        //Commonクラスの準備
        Common common = new Common();
        errmsg = common.getErr(name, address, tel);

        //遷移先への値をセット
        request.setAttribute("id", id);
        request.setAttribute("name", name);
        request.setAttribute("address", address);
        request.setAttribute("tel", tel);
        request.setAttribute("categoryid", categoryid);
        request.setAttribute("errmsg", errmsg);

        if(errmsg == "") {
            ServletContext context = this.getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/EditCheck.jsp");
            dispatcher.forward(request, response);
        } else {
            //System.out.println(errmsg);
            ServletContext context = this.getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher("/Edit.jsp");
            dispatcher.forward(request, response);
        }
    }

}
