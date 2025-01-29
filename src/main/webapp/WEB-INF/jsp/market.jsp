<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Marché des Cryptomonnaies</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="bg-gray-100">
    <div class="container mx-auto px-4 py-8">
        <h1 class="text-3xl font-bold mb-8">Cours des Cryptomonnaies</h1>
        
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <c:forEach items="${cryptocurrencies}" var="crypto">
                <div class="bg-white rounded-lg shadow p-6">
                    <h2 class="text-xl font-semibold">${crypto.name}</h2>
                    <p class="text-gray-600">${crypto.symbol}</p>
                    <p class="text-2xl font-bold mt-2">€${crypto.currentPrice}</p>
                    <div class="mt-4">
                        <a href="/trade/${crypto.id}" class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                            Trader
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    
    <script>
        function refreshPrices() {
            fetch('/api/cryptocurrencies/prices')
                .then(response => response.json())
                .then(data => {
                    // Mise à jour des prix
                });
        }
        
        setInterval(refreshPrices, 10000);
    </script>
</body>
</html>