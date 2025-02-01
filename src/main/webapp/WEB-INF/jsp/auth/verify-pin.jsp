<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vérification du PIN</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 h-screen flex items-center justify-center">
<div class="max-w-md w-full mx-4">
    <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-gray-900">Vérification du PIN</h1>
        <p class="mt-2 text-gray-600">Un code PIN a été envoyé à votre adresse email.</p>
    </div>

    <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
        <form id="pinForm" action="/verify-pin-2fa" method="POST" class="space-y-6">
            <div>
                <label for="pin" class="block text-sm font-medium text-gray-700">
                    Entrez votre code PIN
                </label>
                <div class="mt-1">
                    <input id="pin" name="pin" type="text" required
                           class="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm">
                </div>
            </div>

            <input type="hidden" name="email" id="emailField">

            <div>
                <button type="submit"
                        class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                    Vérifier
                </button>
            </div>
        </form>
    </div>
</div>

<script>
    const email = sessionStorage.getItem('email');
    if (email) {
        document.getElementById('emailField').value = email;
    } else {
        window.location.href = '/login';
    }
</script>


</body>
</html>