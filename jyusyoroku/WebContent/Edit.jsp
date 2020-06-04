<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.ResultSet, jyusyoroku.sep.yt.Common"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/jyusyoroku.css">
<title>住所録管理システム:住所録編集</title>
</head>
<body>
<%
  String id = "";
  String name = "";
  String address = "";
  String tel = "";
  //詳細設計書上はcategoryidを指定している。
  String categoryid = "";
  String errmsg = "";
  ResultSet rs = null;

  Common common = new Common();
  rs = common.getCategoryAll();

  request.setCharacterEncoding("UTF-8");
  id = request.getParameter("id");
  name = request.getParameter("name");
  address = request.getParameter("address");
  tel = request.getParameter("tel");
  categoryid = request.getParameter("categoryid");

  //エラーメッセージ取得(Object型→String型へ変換して格納)
  if(request.getAttribute("errmsg") != null) {
  errmsg = (String)request.getAttribute("errmsg");
  }
%>
  <header>
    <h3>住所録管理システム:住所録編集</h3>
  </header>
  <div class="main">
    <div class="container">
    <form method="post" action="./EditBL">
      <%-- idはhiddenでpostする --%>
      <input type="hidden" name="id" value="<%= id %>">
      <div class="form-label"><label for="name">名前<span class="required-item-mark">*</span>：</label></div><div class="form-part"><input type="text" id="name" name="name" value="<%= name %>"></div>
      <div class="form-label"><label for="address">住所<span class="required-item-mark">*</span>：</label></div><div class="form-part"><input type="text" id="address" name="address" value="<%= address %>"></div>
      <div class="form-label"><label for="tel">電話番号：</label></div><div class="form-part"><input type="text" id="tel" name="tel" value="<%= tel %>"></div>
      <div class="form-label"><label for="categoryid">カテゴリ：</label></div>
      <select id="categoryid" name="categoryid">
      <%
        while(rs.next()) {
      %>
        <option value ="<%=rs.getInt("categoryid") %>"
        <%-- categoryidのリクエスト値に該当するoption要素にselected属性を付与 --%>
        <%
          if(categoryid.equals(Integer.toString(rs.getInt("categoryid")))) out.print("selected");
        %>
        >
        <%=rs.getString("categoryname")%>
        </option>
      <%
        }
      %>
      </select>
      <div class = "error-message"><%= errmsg %></div>
      <div class="buttons">
        <input class="submitform" type="submit" value="確認">
        <a class="btn back" href="./ListBL">戻る</a>
      </div>
      <div class="clear"></div>
    </form>
    </div>
  </div>
  <footer>
  </footer>
</body>
</html>