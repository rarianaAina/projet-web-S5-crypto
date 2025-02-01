<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vérification de l'email</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body class="bg-gray-100 h-screen flex items-center justify-center">

<div class="max-w-md w-full mx-4 text-center">
    <h1 class="text-2xl font-bold text-gray-900">Vérification de l'email</h1>
    <p class="mt-2 text-gray-600">Un email de confirmation a été envoyé à votre adresse.</p>
    <p class="mt-2 text-gray-600">Veuillez vérifier votre boîte mail.</p>

    <div id="status" class="mt-4 text-sm text-gray-700">En attente de confirmation...</div>

    <script>
        let email = "<%= request.getParameter("email") %>"; // Récupérer l'email passé dans l'URL
        let attempts = 0;
        let maxAttempts = 12; // 2 minutes (12 x 10 sec)

        function checkVerification() {
            $.get(`/api/email/check?email=${email}`, function(response) {
                if (response === true) {
                    $("#status").text("Votre email a été vérifié. Redirection en cours...");
                    setTimeout(() => {
                        window.location.href = "/dashboard"; // Redirection après confirmation
                    }, 3000);
                } else {
                    attempts++;
                    if (attempts >= maxAttempts) {
                        $("#status").text("Temps écoulé. Veuillez vérifier votre email.");
                        clearInterval(interval);
                    }
                }
            });
        }

        let interval = setInterval(checkVerification, 10000); // Vérifier toutes les 10 secondes
    </script>
</div>

</body>
</html>
