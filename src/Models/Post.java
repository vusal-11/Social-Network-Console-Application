package Models;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private int postID;
    private String content;
    private User author;
    private List<Comment> comments;

    public Post(int postID, String content, User author) {
        this.postID = postID;
        this.content = content;
        this.author = author;
        this.comments = new ArrayList<>();
    }

    // Геттеры и сеттеры
    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void editComment(int commentID, String newContent) {
        for (Comment comment : comments) {
            if (comment.getCommentID() == commentID) {
                comment.setContent(newContent);
                break;
            }
        }
    }

    public void deleteComment(int commentID) {
        comments.removeIf(comment -> comment.getCommentID() == commentID);
    }
}
