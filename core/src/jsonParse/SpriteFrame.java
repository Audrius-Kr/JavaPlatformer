package jsonParse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SpriteFrame {
    public List <Frame> frames;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Frame {
        @JsonProperty("frame")
        public FrameDetails frameDetails;
    }

    public static class FrameDetails {
        public int x;
        public int y;
        public int w;
        public int h;
    }
}