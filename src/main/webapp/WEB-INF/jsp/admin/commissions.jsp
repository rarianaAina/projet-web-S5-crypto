<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Commissions</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
    <div class="container mx-auto px-4 py-8">
        <h1 class="text-3xl font-bold mb-8">Gestion des Commissions</h1>
        
        <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
            <!-- Modification des commissions -->
            <div class="bg-white rounded-lg shadow p-6">
                <h2 class="text-xl font-semibold mb-6">Modifier les Commissions</h2>
                
                <form id="commissionForm" class="space-y-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700">
                            Commission d'achat (%)
                        </label>
                        <input type="number" name="buyCommission" step="0.01"
                               value="${currentCommission.buyCommission}"
                               class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                    </div>
                    
                    <div>
                        <label class="block text-sm font-medium text-gray-700">
                            Commission de vente (%)
                        </label>
                        <input type="number" name="sellCommission" step="0.01"
                               value="${currentCommission.sellCommission}"
                               class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                    </div>
                    
                    <button type="submit"
                            class="w-full bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                        Mettre à jour
                    </button>
                </form>
            </div>
            
            <!-- Analyse des commissions -->
            <div class="bg-white rounded-lg shadow p-6">
                <h2 class="text-xl font-semibold mb-6">Analyse des Commissions</h2>
                
                <form id="analysisForm" class="space-y-4">
                    <div>
                        <label class="block text-sm font-medium text-gray-700">Cryptomonnaies</label>
                        <div class="mt-2 space-y-2">
                            <c:forEach items="${cryptocurrencies}" var="crypto">
                                <label class="inline-flex items-center">
                                    <input type="checkbox" name="cryptoIds" value="${crypto.id}"
                                           class="rounded border-gray-300 text-blue-600 shadow-sm">
                                    <span class="ml-2">${crypto.name} (${crypto.symbol})</span>
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                    
                    <div class="grid grid-cols-2 gap-4">
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
                    </div>
                    
                    <button type="submit"
                            class="w-full bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">
                        Analyser
                    </button>
                </form>
                
                <div id="analysisResults" class="mt-6 hidden">
                    <h3 class="text-lg font-semibold mb-4">Résultats</h3>
                    <div class="space-y-2">
                        <p>Total des commissions: <span id="totalCommissions"></span> €</p>
                        <p>Moyenne par transaction: <span id="averageCommission"></span> €</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        // Mise à jour des commissions
        document.getElementById('commissionForm').addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const formData = new FormData(e.target);
            const params = new URLSearchParams();
            params.append('buyCommission', formData.get('buyCommission'));
            params.append('sellCommission', formData.get('sellCommission'));
            
            try {
                const response = await fetch('/admin/commissions/update', {
                    method: 'POST',
                    body: params
                });
                
                if (response.ok) {
                    alert('Commissions mises à jour avec succès');
                } else {
                    throw new Error('Erreur lors de la mise à jour');
                }
            } catch (error) {
                console.error('Erreur:', error);
                alert('Une erreur est survenue');
            }
        });
        
        // Analyse des commissions
        document.getElementById('analysisForm').addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const formData = new FormData(e.target);
            const params = new URLSearchParams();
            
            formData.getAll('cryptoIds').forEach(id => params.append('cryptoIds', id));
            params.append('startDate', formData.get('startDate'));
            params.append('endDate', formData.get('endDate'));
            
            try {
                const response = await fetch('/admin/commissions/analytics?' + params.toString());
                const results = await response.json();
                
                document.getElementById('totalCommissions').textContent = results.total.toFixed(2);
                document.getElementById('averageCommission').textContent = results.average.toFixed(2);
                document.getElementById('analysisResults').classList.remove('hidden');
            } catch (error) {
                console.error('Erreur:', error);
                alert('Une erreur est survenue lors de l'analyse');
            }
        });
    </script>
</body>
</html>