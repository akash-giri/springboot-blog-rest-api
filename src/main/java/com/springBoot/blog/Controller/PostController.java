package com.springBoot.blog.Controller;

import com.springBoot.blog.Service.PostService;
import com.springBoot.blog.Utils.AppConstants;
import com.springBoot.blog.payload.PostDto;
import com.springBoot.blog.payload.PostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    //localhost:8080/api/posts

    private PostService postService;

    public PostController(PostService postService)
    {
        this.postService=postService;
    }

    //create blog post rest api
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto)
    {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all posts rest api
//    @GetMapping
//    public List<PostDto> getAllPosts(){
//
//        return postService.getAllPosts();
//    }

    //get all posts rest api using of pagination concepts
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false)int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false)String sortDir
    ){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    //get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id)
    {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //update post by id rest api
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable(name="id") long id)
    {
       PostDto postResponse= postService.updatePost(postDto,id);
       return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //delete post rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name="id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted sucessfully",HttpStatus.OK);
    }
}
