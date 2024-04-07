package edu.ib.networktechnologies.controllers.dto.books;

public class UpdateBookDetailsDto {

    private String genre;

    private String summary;

    private String coverUrl;

    public UpdateBookDetailsDto() {
    }

    public UpdateBookDetailsDto(String genre, String summary, String coverUrl) {
        this.genre = genre;
        this.summary = summary;
        this.coverUrl = coverUrl;
    }

    public String getGenre() {
        return genre;
    }

    public String getSummary() {
        return summary;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

}
