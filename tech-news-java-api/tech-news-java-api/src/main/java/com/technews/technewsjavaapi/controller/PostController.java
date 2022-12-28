package com.technews.technewsjavaapi.controller;

import com.technews.technewsjavaapi.model.Post;
import com.technews.technewsjavaapi.repository.UserRepository;
import com.technews.technewsjavaapi.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class PostController {
    @Autowired
    postRepository repository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/posts")
    public List<Post> getAllPost() {
        List<Post> postList = repository.findAll();
        for (post p: postList) {
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
        }
        return postList;
    }

    @GetMapping("/api/post/{id}")
    public Post getPost(@PathVariable Integer id) {
        Post returnPost = repository.getById(id);
        returnPost.setVoteCount(voteRepository.countVotesByPostId(returnPost.getId()));

        return returnPost;
    }

    @PostMapping("/api/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post addPost(@RequestBody Post post) {
        repository.save(post);
        return post;
    }

    @PutMapping("/api/post/{id}")
    public Post updatePost(@PathVariable int id, @RequestBody Post post) {
        Post tempPost = repository.getById(id);
        tempPost.setTitle(post.getTitle());
        return repository.save(tempPost);
    }

    @putMapping("/api/posts/upvote")
    public String addVote (@RequestBody Vote vote, HttpsServletRequest request) {
        String returnValue = "";

        if(request.getSession(false) != null) {
            Post returnPost = null;

            User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
            vote.setUserId(sessionUser.getID());
            voteRepository.save(vote);

            returnPost = repository.getById(vote.getPostId());
            returnPost.setVoteCount(voteRepository.countVotesByPostId(vote.getPostId()));

            returnValue = " ";
        } else {
            returnValue = "login";
        }

        return returnValue;
    }

    @DeleteMapping("/api/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable int id) {
        repository.deleteById(id);
    }
}
