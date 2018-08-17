<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="https://v3.bootcss.com/favicon.ico">

    <title>Lucene测试</title>

    <!-- Bootstrap core CSS -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

</head>

<body>

<div class="container">
    <h2 class="form-signin-heading">Lucene测试</h2>
    <hr>
    <form class="form-inline" action="select" method="post">
        <div class="form-group">
            <label for="keyword">关键字</label>
            <input type="text" class="form-control" id="keyword" name="keyword" placeholder="电脑 手机">
        </div>
        <button type="submit" class="btn btn-primary">提交查询</button>
    </form>
    <hr>
    <table class="table table-hover">
        <thead>
        <tr>
            <td>商品ID</td>
            <td>商品名称</td>
            <td>价格</td>
            <td>产地</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="u">
            <tr>
                <td>${u.id}</td>
                <td>${u.name}</td>
                <td>${u.price}</td>
                <td>${u.place}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


</body>

</html>

