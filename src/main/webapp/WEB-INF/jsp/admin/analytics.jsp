<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Analyse des Cryptomonnaies</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
    <div class="container mx-auto px-4 py-8">
        <h1 class="text-3xl font-bold mb-8">Analyse des Cryptomonnaies</h1>
        
        <div class="bg-white rounded-lg shadow p-6 mb-8">
            <form id="analysisForm" class="space-y-6">
                <div>
                    <label class="block text-sm font-medium text-gray-700">Type d'analyse</label>
                    <select name="analysisType" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                        <option value="quartile">1er Quartile</option>
                        <option value="max">Maximum</option>
                        <option value="min">Minimum</option>
                        <option value="moyenne">Moyenne</option>
                        <option value="ecart-type">Écart-type</option>
                    </select>
                </div>
                
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
                        class="w-full bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                    Analyser
                </button>
            </form>
        </div>
        
        <div id="results" class="bg-white rounded-lg shadow p-6 hidden">
            <h2 class="text-xl font-semibold mb-4">Résultats</h2>
            <div id="resultsContent" class="space-y-4">
            </div>
        </div>
    </div>
    
    <script>
        document.getElementById('analysisForm').addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const formData = new FormData(e.target);
            const params = new URLSearchParams();
            
            formData.getAll('cryptoIds').forEach(id => params.append('cryptoIds', id));
            params.append('analysisType', formData.get('analysisType'));
            params.append('startDate', formData.get('startDate'));
            params.append('endDate', formData.get('endDate'));
            
            try {
                const response = await fetch('/admin/analytics/calculate?' + params.toString());
                const results = await response.json();
                
                const resultsDiv = document.getElementById('results');
                const resultsContent = document.getElementById('resultsContent');
                resultsContent.innerHTML = '';
                
                Object.entries(results).forEach(([key, value]) => {
                    const [type, cryptoId] = key.split('_');
                    const crypto = Array.from(formData.getAll('cryptoIds'))
                        .find(id => id === cryptoId);
                    
                    const resultElement = document.createElement('div');
                    resultElement.className = 'p-4 bg-gray-50 rounded';
                    resultElement.textContent = `${type}: ${value}`;
                    resultsContent.appendChild(resultElement);
                });
                
                resultsDiv.classList.remove('hidden');
            } catch (error) {
                console.error('Erreur lors de l'analyse:', error);
                alert('Une erreur est survenue lors de l'analyse');
            }
        });
    </script>
</body>
</html>