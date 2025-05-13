package com.example.testnotes;

public class Note {
    public String content;
    public String timestamp;
    public String imagePath;
    private String filePath;

    public Note(String content, String timestamp, Object o) {
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getter for content
    public String getContent() {
        return content;
    }

    // Setter for content
    public void setContent(String content) {
        this.content = content;
    }

    // Getter for timestamp
    public String getTimestamp() {
        return timestamp;
    }

    // Setter for timestamp
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    // Getter for imagePath
    public String getImagePath() {
        return imagePath;
    }

    // Setter for imagePath
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public boolean hasFile() {
        return filePath != null && !filePath.isEmpty();
    }
}