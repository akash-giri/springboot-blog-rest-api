package com.springBoot.blog.Service.impl;

import com.springBoot.blog.Entity.Comment;
import com.springBoot.blog.Entity.Post;
import com.springBoot.blog.Repository.CommentRepository;
import com.springBoot.blog.Repository.PostRepository;
import com.springBoot.blog.Service.CommentService;
import com.springBoot.blog.exception.BlogAPIException;
import com.springBoot.blog.exception.ResourceNotFoundException;
import com.springBoot.blog.payload.CommentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentSerivceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentSerivceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper mapper)
    {
        this.commentRepository=commentRepository;
        this.postRepository=postRepository;
        this.mapper=mapper;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
      Comment comment=mapToEntity(commentDTO);

      //retrieve post entity by id
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        //set post to comment entity
        comment.setPost(post);

        //save comment entity to database
       Comment newComment =commentRepository.save(comment);
       return mapToDto(newComment);

    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        //retrieve comments by postId
        List<Comment> comments=commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        //retrieve post entity by id
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId));

        //retrieve comment by id
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, long commentId, CommentDTO commentRequest) {
        //retrieve post entity by id
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId));

        //retrieve comment by id
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to Post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setMessageBody(commentRequest.getMessageBody());

        Comment updatedComment=commentRepository.save(comment);
        return mapToDto(updatedComment);

    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        //retrieve post entity by id
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId));

        //retrieve comment by id
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to Post");
        }

        commentRepository.delete(comment);


    }

    private CommentDTO mapToDto(Comment comment)
    {
        CommentDTO commentDTO=mapper.map(comment,CommentDTO.class);

//        CommentDTO commentDTO=new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());
//       commentDTO.setMessageBody(comment.getMessageBody());
       return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO)
    {
        Comment comment=mapper.map(commentDTO,Comment.class);
//        Comment comment=new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setMessageBody(commentDTO.getMessageBody());
        return comment;
    }
}
