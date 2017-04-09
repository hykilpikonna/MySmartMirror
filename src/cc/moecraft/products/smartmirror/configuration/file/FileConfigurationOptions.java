package cc.moecraft.products.smartmirror.configuration.file;

import cc.moecraft.products.smartmirror.configuration.MemoryConfiguration;
import cc.moecraft.products.smartmirror.configuration.MemoryConfigurationOptions;

public class FileConfigurationOptions extends MemoryConfigurationOptions {
    private String header = null;
    private boolean copyHeader = true;

    protected FileConfigurationOptions(MemoryConfiguration configuration) {
        super(configuration);
    }

    @Override
    public FileConfiguration configuration() {
        return (FileConfiguration) super.configuration();
    }

    @Override
    public FileConfigurationOptions copyDefaults(boolean value) {
        super.copyDefaults(value);
        return this;
    }

    @Override
    public FileConfigurationOptions pathSeparator(char value) {
        super.pathSeparator(value);
        return this;
    }

    public String header() {
        return header;
    }

    public FileConfigurationOptions header(String value) {
        this.header = value;
        return this;
    }

    public boolean copyHeader() {
        return copyHeader;
    }

    public FileConfigurationOptions copyHeader(boolean value) {
        copyHeader = value;

        return this;
    }
}
