package at.fhv.kabi.samples.models.ImageDescriptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

// Attributes are derived of the HTML attributes of the img Tag
public class ImageDescriptor implements Serializable {

    private String title;
    private String description;
    private ImageType type;
    private String url;
    private int width;
    private int height;
    private List<String> tags;

    public ImageDescriptor() {}

    public ImageDescriptor(String title, String description, ImageType type, String url, int width, int height, List<String> tags) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.url = url;
        this.width = width;
        this.height = height;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public ImageDescriptorFlattened toImageDescriptorWrapper() {
        ImageDescriptorFlattened obj = new ImageDescriptorFlattened();
        obj.setTitle(title);
        obj.setDescription(description);
        obj.setType(type);
        obj.setUrl(url);
        obj.setWidth(width);
        obj.setHeight(height);
        obj.setTags(Arrays.copyOf(tags.toArray(), tags.size(), String[].class));
        return obj;
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
        return "ImageDescriptor{" +
                "name='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", tags=" + tags +
                '}';
    }

    public enum ImageType {
        PNG,
        JPEG,
        SVG,
        GIF,
        TIFF,
        PDF;
    }
}
