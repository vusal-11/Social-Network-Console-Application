package Models;

import java.util.Date;

public class Comment {
    private static int commentCounter = -1;
    private int commentID;
    private String content;
    private Date createdDate;
    private Post post;

    public Comment(String content, Post post) {
        this.commentID = ++commentCounter;
        this.content = content;
        this.createdDate = new Date();
        this.post = post;
    }


    public int getCommentID() {
        return commentID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Post getPost() {
        return post;
    }
}
