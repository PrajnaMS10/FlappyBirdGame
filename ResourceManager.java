package flappybird.util;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static final Map<String, Image> images = new HashMap<>();

    public static void loadResources() {
        images.put("background", loadImage("/flappybirdbg.png"));
        images.put("bird", loadImage("/flappybird.png"));
        images.put("topPipe", loadImage("/toppipe.png"));
        images.put("bottomPipe", loadImage("/bottompipe.png"));
    }

    private static Image loadImage(String path) {
        return new ImageIcon(ResourceManager.class.getResource(path)).getImage();
    }

    public static Image getImage(String key) {
        return images.get(key);
    }
}