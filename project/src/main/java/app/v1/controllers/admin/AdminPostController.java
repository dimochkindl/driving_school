package app.v1.controllers.admin;

import app.v1.services.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin/post")
public class AdminPostController {
    private final PostServiceImpl postService;

    @Autowired
    public AdminPostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping("/deletePost")
    public String deletePost(@RequestParam("id") Long id ){
        postService.remove(id);
        return "redirect:/admin/";
    }
}
