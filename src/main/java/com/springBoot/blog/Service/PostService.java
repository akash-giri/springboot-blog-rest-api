package com.springBoot.blog.Service;

import com.springBoot.blog.payload.PostDto;
import com.springBoot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    //use get all post without pagination
//    List<PostDto> getAllPosts();

    //use get all post with pagination
    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto,long id);

    void deletePostById(long id);

}
