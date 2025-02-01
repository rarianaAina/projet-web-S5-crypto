<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>${title} - Crypto Trading</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
<nav class="bg-white shadow-lg">
    <div class="max-w-7xl mx-auto px-4">
        <div class="flex justify-between h-16">
            <div class="flex">
                <div class="flex-shrink-0 flex items-center">
                    <a href="/" class="text-xl font-bold text-blue-600">CryptoTrading</a>
                </div>

                <div class="hidden sm:ml-6 sm:flex sm:space-x-8">
                    <a href="/market"
                       class="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                        Marché
                    </a>

                    <a href="/portfolio"
                       class="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                        Portefeuille
                    </a>

                    <a href="/history"
                       class="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                        Historique
                    </a>

                    <c:if test="${user.role == 'ADMIN'}">
                        <a href="/admin/dashboard"
                           class="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                            Administration
                        </a>
                    </c:if>
                </div>
            </div>

            <div class="flex items-center">
                <div class="flex-shrink-0">
                    <span class="text-gray-700 mr-4">Solde: €${user.balance}</span>
                </div>
                <div class="ml-3 relative">
                    <div class="flex items-center">
                        <img class="h-8 w-8 rounded-full"
                             src="https://ui-avatars.com/api/?name=${user.email}&background=random"
                             alt="${user.email}">
                        <span class="ml-2 text-gray-700">${user.email}</span>
                        <a href="/logout" class="ml-4 text-red-600 hover:text-red-800">Déconnexion</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</nav>

<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">