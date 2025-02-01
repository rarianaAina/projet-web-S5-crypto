<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="layout/header.jsp" %>

<html>
<head>
    <title>Cours Réel des Cryptomonnaies</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<h1>Cours Réel des Cryptomonnaies</h1>
<canvas id="cryptoChart" width="800" height="400"></canvas>

<script>
    var historique = [];

    // Récupérer la date actuelle
    var now = new Date();
    var twoMinutesAgo = new Date(now.getTime() - 2 * 60 * 1000); // Deux minutes avant

    <c:forEach items="${historique}" var="entry">
    // Convertir le timestamp de la donnée en objet Date
    var entryTimestamp = new Date("${entry.timestamp}");

    // Vérifier si la donnée est dans les 2 dernières minutes
    if (entryTimestamp >= twoMinutesAgo) {
        historique.push({
            timestamp: "${entry.timestamp}",
            price: ${entry.price}
        });
    }
    </c:forEach>

    console.log(historique); // Vérifie les données dans la console du navigateur

    var labels = historique.map(entry => entry.timestamp);
    var data = historique.map(entry => entry.price);

    var ctx = document.getElementById('cryptoChart').getContext('2d');
    var cryptoChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Prix des Cryptomonnaies',
                data: data,
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                x: {
                    beginAtZero: false
                }
            }
        }
    });
</script>

</body>
</html>
