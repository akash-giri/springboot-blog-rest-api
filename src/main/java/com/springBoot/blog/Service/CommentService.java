package com.springBoot.blog.Service;

import com.springBoot.blog.Entity.Comment;
import com.springBoot.blog.payload.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(long postId,CommentDTO commentDTO);

    List<CommentDTO> getCommentsByPostId(long postId);

    CommentDTO getCommentById(Long postId,Long commentId);

    CommentDTO updateComment(Long postId,long commentId,CommentDTO commentDTO);

    void deleteComment(Long postId,Long commentId);
}
