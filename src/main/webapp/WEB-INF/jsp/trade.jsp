<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trader ${crypto.name}</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="bg-gray-100">
    <div class="container mx-auto px-4 py-8">
        <h1 class="text-3xl font-bold mb-8">Trader ${crypto.name}</h1>
        
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
            <!-- Graphique -->
            <div class="bg-white rounded-lg shadow p-6">
                <canvas id="priceChart"></canvas>
            </div>
            
            <!-- Formulaire de trading -->
            <div class="bg-white rounded-lg shadow p-6">
                <div class="mb-8">
                    <h2 class="text-xl font-semibold mb-2">Cours actuel</h2>
                    <p class="text-3xl font-bold">€${crypto.currentPrice}</p>
                </div>
                
                <div class="grid grid-cols-2 gap-4">
                    <!-- Achat -->
                    <div>
                        <h3 class="text-lg font-semibold mb-4">Acheter</h3>
                        <form action="/api/trade/buy/${crypto.id}" method="POST">
                            <div class="mb-4">
                                <label class="block text-sm font-medium text-gray-700">Montant</label>
                                <input type="number" step="0.000001" name="amount" 
                                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm" 
                                       required>
                            </div>
                            <button type="submit" 
                                    class="w-full bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">
                                Acheter
                            </button>
                        </form>
                    </div>
                    
                    <!-- Vente -->
                    <div>
                        <h3 class="text-lg font-semibold mb-4">Vendre</h3>
                        <form action="/api/trade/sell/${crypto.id}" method="POST">
                            <div class="mb-4">
                                <label class="block text-sm font-medium text-gray-700">Montant</label>
                                <input type="number" step="0.000001" name="amount" 
                                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm" 
                                       required>
                            </div>
                            <button type="submit" 
                                    class="w-full bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">
                                Vendre
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        // Configuration du graphique
        const ctx = document.getElementById('priceChart').getContext('2d');
        const chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: [],
                datasets: [{
                    label: 'Prix',
                    data: [],
                    borderColor: 'rgb(75, 192, 192)',
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: false
                    }
                }
            }
        });
        
        // Mise à jour du prix en temps réel
        setInterval(() => {
            fetch('/api/cryptocurrencies/prices')
                .then(response => response.json())
                .then(data => {
                    const crypto = data.find(c => c.id === ${crypto.id});
                    if (crypto) {
                        const price = crypto.currentPrice;
                        const now = new Date().toLocaleTimeString();
                        
                        chart.data.labels.push(now);
                        chart.data.datasets[0].data.push(price);
                        
                        if (chart.data.labels.length > 20) {
                            chart.data.labels.shift();
                            chart.data.datasets[0].data.shift();
                        }
                        
                        chart.update();
                    }
                });
        }, 10000);
    </script>
</body>
</html>