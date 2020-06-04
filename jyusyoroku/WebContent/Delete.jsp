<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="jyusyoroku.sep.yt.Common" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/jyusyoroku.css">
<title>住所録管理システム：住所録削除(確認)</title>
</head>
<body>
<%
  String id = "";
  String name = "";
  String address = "";
  String tel = "";
  String categoryid = "";
  String categoryname = "";
  request.setCharacterEncoding("UTF-8");
  id = request.getParameter("id");
  name = request.getParameter("name");
  address = request.getParameter("address");
  tel = request.getParameter("tel");
  categoryid = request.getParameter("categoryid");
  //Commonクラスの準備
  Common common = new Common();
  categoryname = common.getCategoryName(categoryid);

%>
  <header>
    <h3>住所録管理システム:住所録削除(確認)</h3>
  </header>
  <div class="main">
    <h4>下記住所録を削除します。よろしいですか？</h4>
    <div>
      <div><span class="item-name">名前<span class="required-item-mark">*</span>：</span><span class="item-value"><%=name%></span></div>
      <div><span class="item-name">住所<span class="required-item-mark">*</span>：</span><span class="item-value"><%=address%></span></div>
      <div><span class="item-name">電話番号：</span><span class="item-value"><%=tel%></span></div>
      <div><span class="item-name">カテゴリ：</span><span class="item-value"><%=categoryname%></span></div>
    </div>
    <form method="post" action="./DeleteCommit">
    <div class="buttons">
      <input type="hidden" name="id" value="<%=id%>">
      <input class="submitform" type="submit" value="OK">
      <a class="btn back" href="./ListBL">戻る</a>
    </div>
    <div class="clear"></div>
    </form>

  </div>
  <footer>
  </footer>
</body>
</html>