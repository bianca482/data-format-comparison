package at.fhv.kabi.samples.models.BufferedImage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;

public class ImageData implements Serializable {

    private String name;
    private byte[] imageBytes;

    public ImageData() {
    }

    public ImageData(String name, byte[] imageBytes) {
        this.name = name;
        this.imageBytes = imageBytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    @Override
    public String toString() {
        JsonMapper mapper = new JsonMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
    public String toFormattedString() {
        return "ImageData{" +
                "name='" + name + '\'' +
                ", imageBytesLength=" + imageBytes.length +
                '}';
    }
}
