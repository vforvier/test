package com.example.testnotes;

public class Note {
    public String content;
    public String timestamp;
    public String imagePath;
    private String filePath;

    public Note(String content, String timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

    public boolean hasFile() {
        return filePath != null && !filePath.isEmpty();
    }
}