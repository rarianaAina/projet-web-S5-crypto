<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dépôt - CryptoTrading</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">
<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">Dépôt d'argent</h1>

    <div class="bg-white rounded-lg shadow p-6">
        <div class="mb-6">
            <p class="text-gray-600">Solde actuel</p>
            <p class="text-2xl font-bold">€${user.balance}</p>
        </div>

        <form id="depositForm" class="space-y-6">
            <div>
                <label for="amount" class="block text-sm font-medium text-gray-700">
                    Montant à déposer (€)
                </label>
                <input type="number" id="amount" name="amount" min="0" step="0.01" required
                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
            </div>

            <button type="submit"
                    class="w-full bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">
                Demander un dépôt
            </button>
        </form>
    </div>
</div>

<script>
    document.getElementById('depositForm').addEventListener('submit', async (e) => {
        e.preventDefault();

        try {
            const response = await fetch('/api/deposit', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Authorization': localStorage.getItem('token')
                },
                body: new URLSearchParams({
                    amount: e.target.amount.value
                })
            });

            if (response.ok) {
                alert('Votre demande de dépôt a été enregistrée et est en attente de validation.');
                window.location.href = '/portfolio';
            } else {
                throw new Error('Erreur lors de la demande de dépôt');
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Une erreur est survenue lors de la demande de dépôt');
        }
    });
</script>
</body>
</html>