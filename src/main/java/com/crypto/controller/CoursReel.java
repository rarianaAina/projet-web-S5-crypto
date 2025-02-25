package com.crypto.controller;

import com.crypto.model.CryptoHistory;
import com.crypto.repository.CryptoHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CoursReel {

    @Autowired
    private CryptoHistoryRepository cryptoHistoryRepository;

    @GetMapping("/cours-reel")
    public String afficherCoursReel(Model model) {
        List<CryptoHistory> historique = cryptoHistoryRepository.findAll();

        if (historique.isEmpty()) {
            System.out.println("Aucune donnée trouvée dans l'historique !");
        } else {
            System.out.println("Nombre d'entrées dans l'historique : " + historique.size());
        }

        model.addAttribute("historique", historique);
        return "cours-reel";
    }

}