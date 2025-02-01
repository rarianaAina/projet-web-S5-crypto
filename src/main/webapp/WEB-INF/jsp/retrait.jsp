<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Retrait - CryptoTrading</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">
<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">Retrait d'argent</h1>

    <div class="bg-white rounded-lg shadow p-6">
        <div class="mb-6">
            <p class="text-gray-600">Solde disponible</p>
            <p class="text-2xl font-bold">€${user.balance}</p>
        </div>

        <form id="withdrawForm" class="space-y-6">
            <div>
                <label for="amount" class="block text-sm font-medium text-gray-700">
                    Montant à retirer (€)
                </label>
                <input type="number" id="amount" name="amount" min="0" step="0.01"
                       max="${user.balance}" required
                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
            </div>

            <button type="submit"
                    class="w-full bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">
                Demander un retrait
            </button>
        </form>
    </div>
</div>

<script>
    document.getElementById('withdrawForm').addEventListener('submit', async (e) => {
        e.preventDefault();

        try {
            const response = await fetch('/api/withdraw', {
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
                alert('Votre demande de retrait a été enregistrée et est en attente de validation.');
                window.location.href = '/portfolio';
            } else {
                throw new Error('Erreur lors de la demande de retrait');
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Une erreur est survenue lors de la demande de retrait');
        }
    });
</script>
</body>
</html>