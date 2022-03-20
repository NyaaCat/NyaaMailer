package cat.nyaa.nyaamailer.support;

import land.melon.lab.simplelanguageloader.components.Text;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Convert NyaaCore Language to SimpleLanguageLoader
 */
public class LanguageAdapter {
    private final Plugin plugin;
    private final Map<String, Field> langFieldMap = new HashMap<>();

    public LanguageAdapter(Class<?> langCls, Plugin plugin){
        this.plugin = plugin;
        Field[] declaredFields = langCls.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            LanguageKey annotation = declaredField.getAnnotation(LanguageKey.class);
            if (annotation == null){
                continue;
            }
            if (!declaredField.getType().isAssignableFrom(Text.class)){
                continue;
            }
            declaredField.setAccessible(true);
            langFieldMap.put(annotation.value(), declaredField);
        }
    }

    public Text convert(Object langObj, String key){
        try {
            return ((Text) langFieldMap.get(key)
                    .get(langObj));
        } catch (IllegalAccessException e) {
            plugin.getLogger().log(Level.WARNING, "lang: ["+key+"] is not coded, please check in code.");
        }
        return null;
    }
}
