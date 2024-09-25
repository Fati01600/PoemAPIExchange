package dat.dtos;

import dat.entities.Poem;

public class PoemDTO {
    private Long id;
    private String title;
    private String content;
    private String author;


    public PoemDTO(Poem poem) {
        this.id = poem.getId();
        this.title = poem.getTitle();
        this.content = poem.getContent();
        this.author = poem.getAuthor();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
