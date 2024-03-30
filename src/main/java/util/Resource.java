package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.function.Function;

/**
 * Encapsulates either an internal resource read with a getResourceAsStream
 * function, or an external resource read with a File object.
 */
public class Resource {
    private final boolean internal;

    private final String internalPath;
    private final Function<String, InputStream> asStream;
    private final File externalFile;

    public Resource(String internalPath, Function<String, InputStream> asStream) {
        this.internal = true;
        this.internalPath = internalPath;
        this.asStream = asStream;
        this.externalFile = null;
    }

    public Resource(File file) {
        this.internal = false;
        this.externalFile = file;
        this.internalPath = null;
        this.asStream = null;
    }

    public InputStream toStream() {
        if (internal) {
            return asStream.apply(internalPath);
        }

        try {
            return new FileInputStream(externalFile);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
