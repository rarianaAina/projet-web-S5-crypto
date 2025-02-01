<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="layout/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Historique des Transactions</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
    <div class="container mx-auto px-4 py-8">
        <h1 class="text-3xl font-bold mb-8">Historique des Transactions</h1>
        
        <div class="bg-white rounded-lg shadow overflow-hidden">
            <!-- Filtres -->
            <div class="p-4 border-b">
                <form id="filterForm" class="grid grid-cols-1 md:grid-cols-4 gap-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700">Utilisateur</label>
                        <select name="userId" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                            <option value="">Tous les utilisateurs</option>
                            <c:forEach items="${users}" var="user">
                                <option value="${user.id}">${user.email}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700">Cryptomonnaies</label>
                        <select name="cryptoIds" multiple class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                            <c:forEach items="${cryptocurrencies}" var="crypto">
                                <option value="${crypto.id}">${crypto.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700">Date de début</label>
                        <input type="datetime-local" name="startDate"
                               class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700">Date de fin</label>
                        <input type="datetime-local" name="endDate"
                               class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                    </div>
                    
                    <div class="md:col-span-4">
                        <button type="submit"
                                class="w-full bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                            Filtrer
                        </button>
                    </div>
                </form>
            </div>
            
            <!-- Tableau des transactions -->
            <div class="overflow-x-auto">
                <table class="min-w-full divide-y divide-gray-200">
                    <thead class="bg-gray-50">
                        <tr>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Utilisateur
                            </th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Cryptomonnaie
                            </th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Type
                            </th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Montant
                            </th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Prix
                            </th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Total
                            </th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                                Date
                            </th>
                        </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-gray-200">
                        <c:forEach items="${transactions}" var="transaction">
                            <tr>
                                <td class="px-6 py-4 whitespace-nowrap">
                                    <div class="flex items-center">
                                        <div class="flex-shrink-0 h-10 w-10">
                                            <img class="h-10 w-10 rounded-full"
                                                 src="https://ui-avatars.com/api/?name=${transaction.user.email}&background=random"
                                                 alt="${transaction.user.email}">
                                        </div>
                                        <div class="ml-4">
                                            <div class="text-sm font-medium text-gray-900">
                                                ${transaction.user.email}
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap">
                                    <div class="text-sm text-gray-900">
                                        ${transaction.cryptocurrency.name}
                                    </div>
                                    <div class="text-sm text-gray-500">
                                        ${transaction.cryptocurrency.symbol}
                                    </div>
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap">
                                    <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full
                                               ${transaction.type == 'BUY' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}">
                                        ${transaction.type}
                                    </span>
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    ${transaction.amount} ${transaction.cryptocurrency.symbol}
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    €${transaction.price}
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    €${transaction.amount * transaction.price}
                                </td>
                                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    ${transaction.timestamp}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    
    <script>
        document.getElementById('filterForm').addEventListener('submit', (e) => {
            e.preventDefault();
            const formData = new FormData(e.target);
            const params = new URLSearchParams();
            
            for (let [key, value] of formData.entries()) {
                if (value) params.append(key, value);
            }
            
            window.location.href = '/history?' + params.toString();
        });
    </script>
</body>
</html>