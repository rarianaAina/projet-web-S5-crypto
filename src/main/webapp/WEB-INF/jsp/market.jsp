<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="layout/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Marché - CryptoTrading</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
<div class="container mx-auto px-4 py-8">
    <div class="flex justify-between items-center mb-8">
        <h1 class="text-3xl font-bold">Marché des Cryptomonnaies</h1>
        <div>
            <span class="text-gray-600">Solde:</span>
            <span class="font-bold">€${user.balance}</span>
        </div>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <c:forEach items="${cryptocurrencies}" var="crypto">
            <div class="bg-white rounded-lg shadow p-6">
                <div class="flex justify-between items-start mb-4">
                    <div>
                        <h2 class="text-xl font-semibold">${crypto.name}</h2>
                        <p class="text-gray-600">${crypto.symbol}</p>
                    </div>
                    <p class="text-2xl font-bold">€${crypto.currentPrice}</p>
                </div>

                <div class="space-y-4">
                    <form onsubmit="handleTrade(event, 'buy', ${crypto.id})" class="space-y-2">
                        <div class="flex space-x-2">
                            <input type="number" name="amount" step="0.000001" min="0" required
                                   class="flex-1 rounded border-gray-300 shadow-sm"
                                   placeholder="Quantité">
                            <button type="submit"
                                    class="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">
                                Acheter
                            </button>
                        </div>
                    </form>

                    <form onsubmit="handleTrade(event, 'sell', ${crypto.id})" class="space-y-2">
                        <div class="flex space-x-2">
                            <input type="number" name="amount" step="0.000001" min="0" required
                                   class="flex-1 rounded border-gray-300 shadow-sm"
                                   placeholder="Quantité">
                            <button type="submit"
                                    class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">
                                Vendre
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script>
    // Stocker le token dans localStorage après la connexion réussie
    const token = '${sessionScope.token}';
    if (token) {
        localStorage.setItem('token', token);
    }

    async function handleTrade(event, type, cryptoId) {
        event.preventDefault();
        const amount = event.target.amount.value;

        try {
            const response = await fetch("/api/trade/"+ type, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Authorization': localStorage.getItem('token')
                },
                body: new URLSearchParams({
                    cryptoId: cryptoId,
                    amount: amount
                })
            });

            if (response.ok) {
                alert(type === 'buy' ? 'Achat effectué avec succès' : 'Vente effectuée avec succès');
                window.location.reload();
            } else {
                const error = await response.text();
                throw new Error(error);
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert(error.message);
        }
    }
</script>
</body>
</html>