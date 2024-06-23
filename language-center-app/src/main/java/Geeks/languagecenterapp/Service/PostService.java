package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.DTO.Request.PostRequest;
import Geeks.languagecenterapp.Model.Enum.PostEnum;
import Geeks.languagecenterapp.Model.PostEntity;
import Geeks.languagecenterapp.Repository.PostRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    //Add Post by admin and return ok , return bad request response otherwise
    public ResponseEntity<Object> add(PostRequest postRequest) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();

        try {
            PostEntity post = new PostEntity();
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setType(postRequest.getType());
            post.setCreatedAt(LocalDateTime.now());
            postRepository.save(post);

            // Create a response object with the success message
            response.put("message","Post added successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Create a response object with the success message
            response.put("message","Something went wrong.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //Search for post by id ...if found -> update info ...else return not found response
    public ResponseEntity<Object> update(PostRequest postRequest, int id) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();
        Optional<PostEntity> post = postRepository.findById(id);
        if (post.isPresent()) {
            try {
                post.get().setTitle(postRequest.getTitle());
                post.get().setContent(postRequest.getContent());
                postRepository.save(post.get());

                // Create a response object with the success message
                response.put("message","Post updated successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message","Something went wrong.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            // Create a response object with the success message
            response.put("message","Post not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //Search for post by id ...if found -> delete info ...else return not found response
    public ResponseEntity<Object> delete(int id) throws JsonProcessingException {
        Map <String,String> response = new HashMap<>();
        Optional<PostEntity> post = postRepository.findById(id);
        if (post.isPresent()) {
            try {
                postRepository.delete(post.get());

                // Create a response object with the success message
                response.put("message","Post deleted successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message","Something went wrong.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            // Create a response object with the success message
            response.put("message","Post not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    //get all posts
    public List<PostEntity> getAll() {
        return postRepository.findAll();
    }

    //get all ads
    public List<PostEntity> getAds() {
        return postRepository.findByType(PostEnum.ADS);
    }
    //get all recent ads
    public List<PostEntity> getRecentAds() {
        return postRepository.findByTypeOrderByCreatedAtDesc(PostEnum.ADS);
    }
    //get all old ads
    public List<PostEntity> getOldAds() {
        return postRepository.findByTypeOrderByCreatedAtAsc(PostEnum.ADS);
    }
    //get all events
    public List<PostEntity> getEvents() {
        return postRepository.findByType(PostEnum.EVENT);
    }
    //get all recent events
    public List<PostEntity> getRecentEvents() {
        return postRepository.findByTypeOrderByCreatedAtDesc(PostEnum.EVENT);
    }
    //get all old events
    public List<PostEntity> getOldEvents() {
        return postRepository.findByTypeOrderByCreatedAtAsc(PostEnum.EVENT);
    }



}
