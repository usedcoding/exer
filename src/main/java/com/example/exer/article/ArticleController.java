package com.example.exer.article;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/article")
@Controller
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping("/list")
    public String articleList(Model model) {
        List<Article> articleList = this.articleService.getList();
        model.addAttribute("articleList", articleList);
        return "article_list";
    }

    @GetMapping("/create")
    public String articleCreate(ArticleForm articleForm) {
        return"article_create";
    }

    @PostMapping("/create")
    public String articleCreate(@RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content) {
        this.articleService.create(subject, content);
        return"redirect:/article/list";
    }

    @GetMapping("/detail/{id}")
    public String articleDetail(Model model, @PathVariable("id") Integer id) {
       Article article = this.articleService.getArticle(id);
       model.addAttribute("article", article);
       return "article_detail";
    }

    @GetMapping("/modify/{id}")
    public String articleModify(Model model,@PathVariable("id")Integer id, ArticleForm articleForm) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);

        articleForm.setSubject(article.getSubject());
        articleForm.setContent(article.getContent());

        return"article_create";
    }

    @PostMapping("/modify/{id}")
    public String articleModify(@PathVariable("id")Integer id, Model model, @Valid ArticleForm articleForm, BindingResult bindingResult) {
        Article article = this.articleService.getArticle(id);

        //에러발생 감지, 결과값에 에러가 발생하면 create로 다시 돌아간다.
        if(bindingResult.hasErrors()) {
            return"article_create";
        }


        this.articleService.modify(article, articleForm.getSubject(), articleForm.getContent());
        model.addAttribute("article",article);
        return String.format("redirect:/article/detail/%s",article.getId());
    }

    @GetMapping("/delete/{id}")
    public String articleDelete(@PathVariable("id")Integer id){
        Article article = this.articleService.getArticle(id);
        this.articleService.delete(article);
        return "redirect:/article/list";
    }

}
