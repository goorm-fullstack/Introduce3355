package Goorm.Introduce.Web.comment;

import Goorm.Introduce.Domain.Comment.Comment;
import Goorm.Introduce.Domain.FireBase.Comment.FirebaseCommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Comment와 관련된 기능을 처리하는 컨트롤러
 * /comment/**로 들어오는 요청을 처리
 */
@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final FirebaseCommentServiceImpl firebaseCommentService;

    @GetMapping("/{id}")
    public String getComment(@PathVariable String id, Model model) throws ExecutionException, InterruptedException {
        Comment comment = firebaseCommentService.getComment(id);
        model.addAttribute("comment", comment);
        return "Comment/Comment";
    }

    @GetMapping("/add")
    public String addCommentForm() {
        return "Comment/addComment";
    }

    @PostMapping("/add")
    public String addComment(@ModelAttribute Comment comment, Model model) throws ExecutionException, InterruptedException {
        firebaseCommentService.insertComment(comment);
        model.addAttribute("comments",firebaseCommentService.findAllComment());
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateComment(@RequestBody Comment comment, Model model) throws ExecutionException, InterruptedException {
        firebaseCommentService.updateComment(comment);
        model.addAttribute("comments",firebaseCommentService.findAllComment());
        return "redirect:/";
    }

    @DeleteMapping("/delete")
    public String deleteComment(@RequestBody Map<String, String> data, Model model) throws ExecutionException, InterruptedException {
        String id = data.get("id");
        firebaseCommentService.deleteComment(id);
        model.addAttribute("comments",firebaseCommentService.findAllComment());
        return "redirect:/";
    }
}
