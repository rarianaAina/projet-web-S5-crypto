<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="layout/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mon Portefeuille</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
    <div class="container mx-auto px-4 py-8">
        <h1 class="text-3xl font-bold mb-8">Mon Portefeuille</h1>
        
        <div class="bg-white rounded-lg shadow p-6 mb-8">
            <h2 class="text-xl font-semibold mb-4">Solde disponible</h2>
            <p class="text-2xl font-bold">€${user.balance}</p>
            <div class="mt-4 space-x-4">
                <a href="/deposit" class="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">
                    Dépôt
                </a>
                <a href="/withdraw" class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">
                    Retrait
                </a>
            </div>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <c:forEach items="${portfolio}" var="holding">
                <div class="bg-white rounded-lg shadow p-6">
                    <h2 class="text-xl font-semibold">${holding.cryptocurrency.name}</h2>
                    <p class="text-gray-600">${holding.cryptocurrency.symbol}</p>
                    <p class="text-2xl font-bold mt-2">${holding.amount} ${holding.cryptocurrency.symbol}</p>
                    <p class="text-lg">Valeur: €${holding.amount * holding.cryptocurrency.currentPrice}</p>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>