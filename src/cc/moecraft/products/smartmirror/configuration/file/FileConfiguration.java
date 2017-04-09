package cc.moecraft.products.smartmirror.configuration.file;

import cc.moecraft.products.smartmirror.Essentials.Validate;
import cc.moecraft.products.smartmirror.configuration.Configuration;
import cc.moecraft.products.smartmirror.configuration.InvalidConfigurationException;
import cc.moecraft.products.smartmirror.configuration.MemoryConfiguration;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public abstract class FileConfiguration extends MemoryConfiguration {

    public FileConfiguration() {
        super();
    }

    public FileConfiguration(Configuration defaults) {
        super(defaults);
    }

    public void save(File file) throws IOException {
        Validate.notNull(file, "File cannot be null");

        Files.createParentDirs(file);

        String data = saveToString();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

        try {
            writer.write(data);
        } finally {
            writer.close();
        }
    }

    public void save(String file) throws IOException {
        Validate.notNull(file, "File cannot be null");

        save(new File(file));
    }

    public abstract String saveToString();

    public void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull(file, "File cannot be null");

        final FileInputStream stream = new FileInputStream(file);

        load(new InputStreamReader(stream, Charsets.UTF_8));
    }

    @Deprecated
    public void load(InputStream stream) throws IOException, InvalidConfigurationException {
        Validate.notNull(stream, "Stream cannot be null");

        load(new InputStreamReader(stream, Charsets.UTF_8));
    }

    public void load(Reader reader) throws IOException, InvalidConfigurationException {
        BufferedReader input = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);

        StringBuilder builder = new StringBuilder();

        try {
            String line;

            while ((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        } finally {
            input.close();
        }

        loadFromString(builder.toString());
    }

    public void load(String file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull(file, "File cannot be null");

        load(new File(file));
    }

    public abstract void loadFromString(String contents) throws InvalidConfigurationException, InvalidConfigurationException, InvalidConfigurationException;

    protected abstract String buildHeader();

    @Override
    public FileConfigurationOptions options() {
        if (options == null) {
            options = new FileConfigurationOptions(this);
        }

        return (FileConfigurationOptions) options;
    }
}