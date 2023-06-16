package com.project.springBlog.Service;

import com.project.springBlog.Repository.PostRepository;
import com.project.springBlog.dto.PostDto;
import com.project.springBlog.exception.PostNotFoundException;
import com.project.springBlog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private AuthService authService;

    @Autowired
    private PostRepository postRepository;

    public List<PostDto> showAllPosts(){
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(Collectors.toList()); // looping over the list
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        return postDto;
    }

    public void createPost(PostDto postDto)  {
        Post post = mapFromPostToDto(postDto);
        postRepository.save(post);
    }

    private Post mapFromPostToDto(PostDto postDto) {
        Post post = new Post();
        postDto.setTitle(postDto.getTitle());
        postDto.setContent(postDto.getContent());
        User loggeedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(Instant.now());
        post.setUpdatedOn(Instant.now());
        postDto.setUsername(loggeedInUser.getUsername());
        return post;
    }
    private PostDto readSinglePost(Long id) throws PostNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("For id" + id));
        return mapFromPostToDto(post);
    }
}
