<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
 import="java.sql.ResultSet"
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/jyusyoroku.css">
<title>住所録管理システム：住所録一覧</title>
</head>
<body>
<%!
//DB取得用変数
ResultSet rs = null;
//総件数
int listCnt = 0;
//現在のページ
String nowPage = "";
//最大ページ
int maxPage = 0;

String SearchName = "";
%>
<%!
  //電話番号にハイフンを付け足すメソッド(電話番号が空文字ならば何もせずに返す)
  String appendHyphen(String tel) {
    if(tel.isEmpty()) {
      return tel;
    } else {
      StringBuilder telAppendedHyphen = new StringBuilder(tel);
        telAppendedHyphen.insert(3, "-").insert(8,"-");
      return telAppendedHyphen.toString();
    }
  }

  //ページネーションを作成するメソッド
  String createPagenation(int maxPage, String nowPage) {
  //ページネーション部のHTML格納用変数
    String pagenation = "<ul>";
    //現在表示中のページ (引数として受け取ったnowPageをint型に変換)
    int currentPage = Integer.parseInt(nowPage);
    //表示させたいページリンク数の最大数
    int PAGE_RANGE = 5;
    //現在表示中のページの前後に表示したいページリンク数
    int PAGE_GAP = 2;
    //現在表示中のページを基準にしたページリンクの開始ページ
    int currentStartPage;
    //現在表示中のページを基準にしたページリンクの終了ページ
    int currentEndPage;

    //currentStartPageとcurrentEndPageを決定する。
    if(PAGE_RANGE < maxPage) {
      //最大ページ数がPAGE_RANGEより大きい場合
      currentStartPage = currentPage - PAGE_GAP;
      currentEndPage = currentPage + PAGE_GAP;

      if(currentStartPage < 1) {
        currentStartPage = 1;
        currentEndPage = currentStartPage + (PAGE_RANGE - 1);
      }
      if(currentEndPage > maxPage) {
        currentEndPage = maxPage;
        currentStartPage = currentEndPage - (PAGE_RANGE - 1);
      }

    } else {
      //最大ページ数がPAGE_RANGE以下の場合
      currentStartPage = 1;
      currentEndPage = maxPage;
    }

    //最初のページへのリンク「<<」と 現在表示ページの1つ前のページへのリンク「<」を作成
    if(currentPage == 1) {
      pagenation += "<li><<</li>";
      pagenation += "<li><</li>";
    } else {
      pagenation += "<li><a href=\"./ListBL?Page=1\"><<</a></li>";
      pagenation += "<li><a href=\"./ListBL?Page=" + (currentPage - 1) + "\"><</a></li>";
    }

    //ページ数リンクの作成
    for(int i = currentStartPage; i <= currentEndPage; i++) {
      if(currentPage == i) {
        pagenation += "<li class=\"current-page\">" + i + "</li>";
      } else {
        pagenation += "<li><a href=\"./ListBL?Page=" + i + "\">" + i + "</a></li>";
      }
    }

    //現在表示ページの1つ先のページへのリンク「>」と 最後のページへのリンク「>>」を作成
    if(currentPage == maxPage) {
      pagenation += "<li>></li>";
      pagenation +=	"<li>>></li>";
    } else {
      pagenation += "<li><a href=\"./ListBL?Page=" + (currentPage + 1) + "\">></a></li>";
      pagenation += "<li><a href=\"./ListBL?Page=" + maxPage + "\">>></a></li>";
    }
    pagenation += "</ul>";

    return pagenation;
  }
%>
<%
  request.setCharacterEncoding("UTF-8");

  nowPage = request.getAttribute("Page").toString();
  listCnt = Integer.parseInt(request.getAttribute("listCnt").toString());
  maxPage = listCnt / 10;

  if(maxPage == 0) maxPage = 1;
  //10件に満たない端数分のページ数追加処理
  if(maxPage != 1) {
    if(!(listCnt % 10 == 0)) maxPage += 1 ;
  }

  if(request.getAttribute("SearchName") != null) {
    SearchName = request.getAttribute("SearchName").toString();
  }
  request.setAttribute("SearchName", SearchName);
  rs = (ResultSet)request.getAttribute("Result");
%>
  <header>
    <h3>住所録管理システム:住所録一覧</h3>
    <div class="header-left"><a class="btn add" href="Add.jsp">新規登録</a></div>
    <div class="header-right">
      <form method="post" action="./ListBL">
        <div class="search-row"><label for="searchaddress">住所：</label><input type="text" id="searchaddress" name="SearchName" value="<%= SearchName %>"></div>
        <div class="search-row"><label for="searchaddress">　　　</label><input class="search-submit" type="submit" value="検索"></div>
      </form>
    </div>
    <div class="clear"></div>
  </header>
  <div class="main">
    <div class="pagenation"><%= createPagenation(maxPage, nowPage) %></div>
    <!-- 問い合わせ結果表示用table -->
    <table class="table-address-book-list">
      <thead>
        <tr>
          <th class="th-no">No.</th>
          <th class="th-name">名前</th>
          <th class="th-address">住所</th>
          <th class="th-tel">電話番号</th>
          <th class="th-category">カテゴリ</th>
          <th colspan="2" class="th-edit-delete"></th>
        </tr>
      </thead>
      <tbody>
      <%
        while(rs.next()) {
      %>
        <tr>
          <td><%=rs.getInt("id") %></td>
          <td><%=rs.getString("name") %></td>
          <%-- マウスオーバー用クラスmouse-over-address-data(住所セルクラスaddress-dataをマウスオーバーするまで非表示) --%>
          <td><div class="address-data"><%=rs.getString("address") %></div><div class="mouse-over-address-data"><%=rs.getString("address") %></div></td>
          <%-- 電話番号については"XXX-XXXX-XXXX"とハイフンで区切る --%>
          <td><%=appendHyphen(rs.getString("tel"))%></td>
          <td><%=rs.getString("categoryname") %></td>
          <td>
          <%-- 編集ボタン --%>
          <form method="post" action="./Edit.jsp">
            <input type="hidden" name="id" value="<%=rs.getInt("id") %>">
            <input type="hidden" name="name" value="<%=rs.getString("name") %>">
            <input type="hidden" name="address" value="<%=rs.getString("address") %>">
            <input type="hidden" name="tel" value="<%= appendHyphen(rs.getString("tel")) %>">
            <input type="hidden" name="categoryid" value="<%=rs.getString("categoryid") %>">
            <input class="edit" type="submit" value="編集">
          </form>
          </td>
          <td>
          <%-- 削除ボタン --%>
          <form method="post" action="./Delete.jsp">
            <input type="hidden" name="id" value="<%=rs.getInt("id") %>">
            <input type="hidden" name="name" value="<%=rs.getString("name") %>">
            <input type="hidden" name="address" value="<%=rs.getString("address") %>">
            <input type="hidden" name="tel" value="<%=appendHyphen(rs.getString("tel"))%>">
            <input type="hidden" name="categoryid" value="<%=rs.getString("categoryid") %>">
            <input class="delete" type="submit" value="削除">
          </form>
          </td>
        </tr>
      <%
        }
      %>
     </tbody>
   </table>
   <div class="pagenation"><%= createPagenation(maxPage, nowPage) %></div>
 </div>
 <footer>
   <div class="footer-left"><a class="btn add" href="Add.jsp">新規登録</a></div>
 </footer>
</body>
</html>