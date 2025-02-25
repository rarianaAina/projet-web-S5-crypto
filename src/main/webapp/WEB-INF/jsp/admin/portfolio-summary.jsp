<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Résumé des Portefeuilles</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">Résumé des Portefeuilles</h1>

    <div class="bg-white rounded-lg shadow overflow-hidden">
        <div class="p-4">
            <form id="filterForm" class="flex items-end space-x-4">
                <div>
                    <label class="block text-sm font-medium text-gray-700">Date maximale</label>
                    <input type="datetime-local" name="maxDate"
                           class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                </div>
                <button type="submit"
                        class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                    Filtrer
                </button>
            </form>
        </div>

        <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Utilisateur
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Total Achats
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Total Ventes
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Valeur Portefeuille
                    </th>
                </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                <c:forEach items="${summaries}" var="summary">
                    <tr>
                        <td class="px-6 py-4 whitespace-nowrap">
                            <div class="flex items-center">
                                <div class="flex-shrink-0 h-10 w-10">
                                    <img class="h-10 w-10 rounded-full"
                                         src="https://ui-avatars.com/api/?name=${fn:escapeXml(summary.userEmail)}&background=random"
                                         alt="${summary.userEmail}">
                                </div>
                                <div class="ml-4">
                                    <div class="text-sm font-medium text-gray-900">
                                            ${summary.userEmail}
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            <div class="text-sm text-gray-900">€${summary.totalBuys}</div>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            <div class="text-sm text-gray-900">€${summary.totalSells}</div>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            <div class="text-sm text-gray-900">€${summary.portfolioValue}</div>
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
        const maxDate = e.target.elements.maxDate.value;
        if (maxDate) {
            window.location.href = "/admin/portfolio-summary?maxDate="+ encodeURIComponent(maxDate);
        }
    });
</script>
</body>
</html>
