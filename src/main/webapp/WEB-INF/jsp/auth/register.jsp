<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inscription - CryptoTrading</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 h-screen flex items-center justify-center">
<div class="max-w-md w-full mx-4">
    <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-gray-900">CryptoTrading</h1>
        <p class="mt-2 text-gray-600">Créez votre compte</p>
    </div>

    <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
        <form id="registrationForm" class="space-y-6">
            <div>
                <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
                <div class="mt-1">
                    <input id="email" name="email" type="email" required
                           class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
                </div>
            </div>

            <div>
                <label for="firstName" class="block text-sm font-medium text-gray-700">Prénom</label>
                <div class="mt-1">
                    <input id="firstName" name="firstName" type="text" required
                           class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
                </div>
            </div>

            <div>
                <label for="lastName" class="block text-sm font-medium text-gray-700">Nom</label>
                <div class="mt-1">
                    <input id="lastName" name="lastName" type="text" required
                           class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
                </div>
            </div>

            <div>
                <label for="password" class="block text-sm font-medium text-gray-700">Mot de passe</label>
                <div class="mt-1">
                    <input id="password" name="password" type="password" required
                           class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
                </div>
            </div>

            <div>
                <label for="confirmPassword" class="block text-sm font-medium text-gray-700">Confirmer le mot de passe</label>
                <div class="mt-1">
                    <input id="confirmPassword" name="confirmPassword" type="password" required
                           class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
                </div>
            </div>

            <div>
                <button type="submit"
                        class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                    S'inscrire
                </button>
            </div>
        </form>

        <div class="mt-6">
            <div class="relative">
                <div class="absolute inset-0 flex items-center">
                    <div class="w-full border-t border-gray-300"></div>
                </div>
                <div class="relative flex justify-center text-sm">
                    <span class="px-2 bg-white text-gray-500">
                        Déjà un compte ?
                    </span>
                </div>
            </div>

            <div class="mt-6">
                <a href="/login"
                   class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-blue-600 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                    Se connecter
                </a>
            </div>
        </div>
    </div>
</div>

<script>
    document.getElementById('registrationForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const formData = new FormData(event.target);

        try {
            const response = await fetch('/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: formData.get('email'),
                    password: formData.get('password'),
                    confirmPassword: formData.get('confirmPassword'),
                    firstName: formData.get('firstName'),
                    lastName: formData.get('lastName')
                })
            });

            if (response.ok) {
                const encodedEmail = encodeURIComponent(formData.get('email'));
                window.location.href = `/validation-email?email=${encodedEmail}`;
            } else {
                const error = await response.text();
                alert(error);
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de l\'inscription');
        }
    });
</script>
</body>
</html>
