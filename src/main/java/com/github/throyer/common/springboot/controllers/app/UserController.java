package com.github.throyer.common.springboot.controllers.app;

import com.github.throyer.common.springboot.domain.models.pagination.Page;
import com.github.throyer.common.springboot.domain.models.pagination.Pagination;
import com.github.throyer.common.springboot.domain.models.shared.Type;
import com.github.throyer.common.springboot.domain.repositories.UserRepository;
import com.github.throyer.common.springboot.domain.services.user.RemoveUserService;
import com.github.throyer.common.springboot.domain.services.user.dto.SearchUser;
import com.github.throyer.common.springboot.utils.Toasts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasAnyAuthority('ADM')")
@RequestMapping("/app/users")
public class UserController {
    
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private RemoveUserService removeService;
    
    @GetMapping
    public String index(Model model, Pagination pagination, Sort sort, SearchUser search) {
        
        var page = Page.of(repository.findSimplifiedUsers(pagination.build()));
        
        model.addAttribute("page", page);
        
        return "app/users/index";
    }
    
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        removeService.remove(id);
        
        Toasts.add(redirect, "Usuário deletado com sucesso.", Type.SUCCESS);
        
        return "redirect:/app/users";
    }
}