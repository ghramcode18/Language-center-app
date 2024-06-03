package Geeks.languagecenterapp.Controller;
import Geeks.languagecenterapp.DTO.Request.PostRequest;
import Geeks.languagecenterapp.Model.PostEntity;
import Geeks.languagecenterapp.Service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;
    //Create post
    @PostMapping("/add")
    public ResponseEntity<Object> addPost(@RequestBody PostRequest body) throws JsonProcessingException {
        return postService.add(body);
    }
    //update post
    @PostMapping("/update/{id}")
    public ResponseEntity<Object>updatePost(@PathVariable("id") int id , @RequestBody PostRequest body)throws JsonProcessingException{
        return postService.update(body, id);
    }
    //delete post
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable("id") int id ) throws JsonProcessingException {
        return postService.delete(id);
    }
    //get All posts
    @GetMapping("/get/all")
    public ResponseEntity<List<PostEntity>> getAllPosts(){
        return ResponseEntity.ok(postService.getAll());
    }
    //get All ads
    @GetMapping("/get/ads")
    public ResponseEntity<List<PostEntity>> getAllAds(){
        return ResponseEntity.ok(postService.getAds());
    }
    //get All recent ads
    @GetMapping("/get/ads/desc")
    public ResponseEntity<List<PostEntity>> getAllRecentAds(){
        return ResponseEntity.ok(postService.getRecentAds());
    }
    //get All old ads
    @GetMapping("/get/ads/asc")
    public ResponseEntity<List<PostEntity>> getAllOldAds(){
        return ResponseEntity.ok(postService.getOldAds());
    }
    //get All events
    @GetMapping("/get/events")
    public ResponseEntity<List<PostEntity>> getAllEvents(){
        return ResponseEntity.ok(postService.getEvents());
    }
    //get All recent events
    @GetMapping("/get/events/desc")
    public ResponseEntity<List<PostEntity>> getAllRecentEvents(){
        return ResponseEntity.ok(postService.getRecentEvents());
    }
    //get All old events
    @GetMapping("/get/events/asc")
    public ResponseEntity<List<PostEntity>> getAllOldEvents(){
        return ResponseEntity.ok(postService.getOldEvents());
    }


}
