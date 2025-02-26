package at.fhv.kabi.samples.models.ImageDescriptor;

import java.util.Arrays;

// For INI, because it cannot handle Lists
public class ImageDescriptorFlattened {

    private String title;
    private String description;
    private ImageDescriptor.ImageType type;
    private String url;
    private int width;
    private int height;
    private String[] tags;

    public ImageDescriptorFlattened() {}

    public ImageDescriptorFlattened(String title, String description, ImageDescriptor.ImageType type, String url, int width, int height, String[] tags) {
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

    public ImageDescriptor.ImageType getType() {
        return type;
    }

    public void setType(ImageDescriptor.ImageType type) {
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

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getTags() {
        return tags;
    }

    public ImageDescriptor toImageDescriptor() {
        ImageDescriptor obj = new ImageDescriptor();
        obj.setTitle(title);
        obj.setDescription(description);
        obj.setType(type);
        obj.setUrl(url);
        obj.setWidth(width);
        obj.setHeight(height);
        obj.setTags(Arrays.asList(tags));

        return obj;
    }
}
