package com.example.blog.controllers;

import com.example.blog.config.AppConstants;
import com.example.blog.payloads.ApiResponse;
import com.example.blog.payloads.PostDto;
import com.example.blog.payloads.PostResponse;
import com.example.blog.services.FileService;
import com.example.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private  String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer categoryId,@PathVariable Integer userId){
        PostDto post=postService.createPost(postDto,categoryId,userId);
        return new  ResponseEntity<PostDto>(post,HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId){
        PostDto postDto1=postService.updatePost(postDto,postId);
        return ResponseEntity.ok(postDto1);
    }

    @GetMapping("/user/{userId}/posts")
    public  ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,
                                                       @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                       @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                       @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
        PostResponse postResponse= postService.getPostsByUser(userId,pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/category/{categoryId}/posts")
    public  ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,
                                                           @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                           @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                           @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
        PostResponse postResponse= postService.getPostsByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/posts/{postId}")
    public  ResponseEntity<PostDto> getPostByPostId(@PathVariable Integer postId){
        PostDto postDto= postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }
    @GetMapping("/posts")
    public  ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                     @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
        PostResponse postResponse= postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/posts/{postId}")
    public  ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
      postService.deletePost(postId);
        return ResponseEntity.ok(new ApiResponse("post deleted successfully",true));
    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
        List<PostDto> postDtos=postService.searchPosts(keywords);
        return  ResponseEntity.ok(postDtos);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                         @PathVariable Integer postId) throws IOException {
        PostDto postDto =postService.getPostById(postId);
      String fileName= fileService.uploadImage(path,image);
      postDto.setImageName(fileName);
      PostDto postDto1=postService.updatePost(postDto,postId);
     return  ResponseEntity.ok(postDto1);
    }

    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response) throws IOException {
        InputStream resource=fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}
