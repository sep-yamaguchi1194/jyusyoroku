<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="jyusyoroku.sep.yt.Common" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/jyusyoroku.css">
<title>住所録管理システム：住所録登録(確認)</title>
</head>
<body>
<%
  String name = "";
  String address = "";
  String tel = "";
  String categoryid = "";
  String categoryname = "";

  request.setCharacterEncoding("UTF-8");

  name = request.getParameter("name");
  address = request.getParameter("address");
  tel = request.getParameter("tel");
  categoryid = request.getParameter("categoryid");
  //Commonクラス
  Common common = new Common();
  categoryname = common.getCategoryName(categoryid);
%>
  <header>
    <h3>住所録管理システム:住所録登録(確認)</h3>
  </header>
  <div class="main">
    <div class="container">
      <div class="item-name">名前<span class="required-item-mark">*</span>：</div><div class="item-value"><%=name%></div>
      <div class="item-name">住所<span class="required-item-mark">*</span>：</div><div class="item-value"><%=address%></div>
      <div class="item-name">電話番号：</div><div class="item-value"><%=tel%></div>
      <div class="item-name">カテゴリ：</div><div class="item-value"><%=categoryname%></div>
    <form method="post" action="./AddCommit">
      <input type="hidden" name="name" value="<%=name%>">
      <input type="hidden" name="address" value="<%=address%>">
      <input type="hidden" name="tel" value="<%=tel%>">
      <input type="hidden" name="categoryid" value="<%=categoryid%>">
      <div class="buttons">
        <input class="submitform" type="submit" value="登録">
        <input class="submitform" type="submit" formaction="./Add.jsp" value="戻る">
      </div>
      <div class="clear"></div>
    </form>
    </div>
  </div>
  <footer>
  </footer>
</body>
</html>