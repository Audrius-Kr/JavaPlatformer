package jsonParse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class ParseJson {
    private ObjectMapper objectMapper;
    private SpriteFrame spriteFrame;
    FileHandle fileHandle;

    public ParseJson(String path) {
        objectMapper = new ObjectMapper();
        try {
            FileHandle fileHandle = Gdx.files.internal(path);
            spriteFrame = objectMapper.readValue(fileHandle.read(), SpriteFrame.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SpriteFrame getSpriteFrame() {
        return this.spriteFrame;
    }
}
