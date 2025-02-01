<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="Dashboard Administrateur" />
<%@ include file="../layout/header.jsp" %>

<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
    <!-- Statistiques générales -->
    <div class="bg-white rounded-lg shadow p-6">
        <h2 class="text-xl font-semibold mb-4">Statistiques</h2>
        <div class="space-y-4">
            <div>
                <p class="text-gray-600">Utilisateurs actifs</p>
                <p class="text-2xl font-bold">${stats.activeUsers}</p>
            </div>
            <div>
                <p class="text-gray-600">Transactions aujourd'hui</p>
                <p class="text-2xl font-bold">${stats.todayTransactions}</p>
            </div>
            <div>
                <p class="text-gray-600">Volume total (24h)</p>
                <p class="text-2xl font-bold">€${stats.dailyVolume}</p>
            </div>
        </div>
    </div>
    
    <!-- Demandes en attente -->
    <div class="bg-white rounded-lg shadow p-6">
        <h2 class="text-xl font-semibold mb-4">Demandes en attente</h2>
        <div class="space-y-4">
            <c:forEach items="${pendingRequests}" var="request">
                <div class="border-b pb-4">
                    <div class="flex justify-between items-start">
                        <div>
                            <p class="font-medium">${request.user.email}</p>
                            <p class="text-sm text-gray-600">
                                ${request.type == 'DEPOSIT' ? 'Dépôt' : 'Retrait'} de €${request.amount}
                            </p>
                            <p class="text-xs text-gray-500">${request.createdAt}</p>
                        </div>
                        <div class="flex space-x-2">
                            <button onclick="processRequest(${request.id}, 'APPROVED')"
                                    class="bg-green-500 text-white px-3 py-1 rounded text-sm hover:bg-green-600">
                                Approuver
                            </button>
                            <button onclick="processRequest(${request.id}, 'REJECTED')"
                                    class="bg-red-500 text-white px-3 py-1 rounded text-sm hover:bg-red-600">
                                Rejeter
                            </button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    
    <!-- Navigation rapide -->
    <div class="bg-white rounded-lg shadow p-6">
        <h2 class="text-xl font-semibold mb-4">Navigation rapide</h2>
        <div class="grid grid-cols-2 gap-4">
            <a href="/admin/analytics" 
               class="bg-blue-50 p-4 rounded-lg text-center hover:bg-blue-100">
                <p class="font-medium">Analyses</p>
            </a>
            <a href="/admin/commissions" 
               class="bg-blue-50 p-4 rounded-lg text-center hover:bg-blue-100">
                <p class="font-medium">Commissions</p>
            </a>
            <a href="/admin/portfolio-summary" 
               class="bg-blue-50 p-4 rounded-lg text-center hover:bg-blue-100">
                <p class="font-medium">Portefeuilles</p>
            </a>
            <a href="/history" 
               class="bg-blue-50 p-4 rounded-lg text-center hover:bg-blue-100">
                <p class="font-medium">Historique</p>
            </a>
        </div>
    </div>
</div>

<script>
    async function processRequest(requestId, status) {
        try {
            const response = await fetch(`/admin/deposits/${requestId}/process`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `status=${status}`
            });
            
            if (response.ok) {
                window.location.reload();
            } else {
                throw new Error('Erreur lors du traitement de la demande');
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Une erreur est survenue lors du traitement de la demande');
        }
    }
</script>

<%@ include file="../layout/footer.jsp" %>