package cc.moecraft.products.smartmirror.configuration;

import java.util.Map;

public interface Configuration extends ConfigurationSection
{

    public abstract void addDefault(String path, Object value);

    public abstract void addDefaults(Map<String, Object> defaults);

    public abstract void addDefaults(Configuration defaults);

    public abstract void setDefaults(Configuration defaults);

    public abstract Configuration getDefaults();

    public abstract ConfigurationOptions options();
}
