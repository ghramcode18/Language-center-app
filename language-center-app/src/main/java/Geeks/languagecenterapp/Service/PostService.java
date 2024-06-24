package Geeks.languagecenterapp.Service;

import Geeks.languagecenterapp.DTO.Request.PostRequest;
import Geeks.languagecenterapp.DTO.Response.CourseDayResponse;
import Geeks.languagecenterapp.DTO.Response.CourseResponse;
import Geeks.languagecenterapp.DTO.Response.PostResponse;
import Geeks.languagecenterapp.Model.CourseEntity;
import Geeks.languagecenterapp.Model.CourseImageEntity;
import Geeks.languagecenterapp.Model.Enum.ImageEnum;
import Geeks.languagecenterapp.Model.Enum.PostEnum;
import Geeks.languagecenterapp.Model.Enum.PostImageEnum;
import Geeks.languagecenterapp.Model.ImageEntity;
import Geeks.languagecenterapp.Model.PostEntity;
import Geeks.languagecenterapp.Repository.CourseImageRepository;
import Geeks.languagecenterapp.Repository.PostRepository;
import Geeks.languagecenterapp.Tools.FilesManagement;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CourseImageRepository courseImageRepository;

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
            String cover = FilesManagement.uploadSingleFile(postRequest.getCover());
            if (cover != null) {
                CourseImageEntity imageEntity = new CourseImageEntity();
                // initialize Image Object
                imageEntity.setImgUrl(cover);
                imageEntity.setPost(post);
                imageEntity.setImageType(PostImageEnum.Cover_Img);
                courseImageRepository.save(imageEntity);
            }
            List<String> imagesUrl = FilesManagement.uploadMultipleFile(postRequest.getImages());
            if (!imagesUrl.isEmpty()) {
                for (String url : imagesUrl) {
                    CourseImageEntity imageEntity = new CourseImageEntity();
                    imageEntity.setImgUrl(url);
                    imageEntity.setPost(post);
                    imageEntity.setImageType(PostImageEnum.Post_Img);
                    courseImageRepository.save(imageEntity);
                }
            }

            // Create a response object with the success message
            response.put("message","Post added successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Create a response object with the success message
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
                String imageUrl = FilesManagement.uploadSingleFile(postRequest.getCover());
                if (imageUrl != null) {
                    CourseImageEntity imageEntity = new CourseImageEntity();
                    // initialize Image Object
                    imageEntity.setImgUrl(imageUrl);
                    imageEntity.setPost(post.get());
                    imageEntity.setImageType(PostImageEnum.Cover_Img);
                    courseImageRepository.save(imageEntity);
                }
                List<String> imagesUrl = FilesManagement.uploadMultipleFile(postRequest.getImages());
                if (!imageUrl.isEmpty()) {
                    for (String url : imagesUrl) {
                        CourseImageEntity imageEntity = new CourseImageEntity();
                        imageEntity.setImgUrl(url);
                        imageEntity.setPost(post.get());
                        imageEntity.setImageType(PostImageEnum.Post_Img);
                        courseImageRepository.save(imageEntity);
                    }
                }
                postRepository.save(post.get());

                // Create a response object with the success message
                response.put("message","Post updated successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Create a response object with the success message
                response.put("message","Something went wrong.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Create a response object with the success message
            response.put("message","Post not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    //get all posts
    public List<PostResponse> getAll() {
        List<PostEntity> posts = postRepository.findAll();
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Convert CourseEntity to CourseDTO
    private PostResponse convertToDTO(PostEntity post) {
        PostResponse dto = new PostResponse();
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setType(post.getType());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setImages(courseImageRepository.findByPostId(post.getId()));
        return dto;
    }

    //get all ads
    public List<PostResponse> getAds() {
        List<PostEntity> posts = postRepository.findByType(PostEnum.ADS);
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    //get all recent ads
    public List<PostResponse> getRecentAds() {
        List<PostEntity> posts = postRepository.findByTypeOrderByCreatedAtDesc(PostEnum.ADS);
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    //get all old ads
    public List<PostResponse> getOldAds() {
        List<PostEntity> posts = postRepository.findByTypeOrderByCreatedAtAsc(PostEnum.ADS);
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    //get all events
    public List<PostResponse> getEvents() {
        List<PostEntity> posts = postRepository.findByType(PostEnum.EVENT);
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    //get all recent events
    public List<PostResponse> getRecentEvents() {
        List<PostEntity> posts = postRepository.findByTypeOrderByCreatedAtDesc(PostEnum.EVENT);
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    //get all old events
    public List<PostResponse> getOldEvents() {
        List<PostEntity> posts = postRepository.findByTypeOrderByCreatedAtAsc(PostEnum.EVENT);
        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }



}
