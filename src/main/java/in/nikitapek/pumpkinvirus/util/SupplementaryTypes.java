package in.nikitapek.pumpkinvirus.util;

import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;

import java.lang.reflect.Type;

@SuppressWarnings("rawtypes")
final class SupplementaryTypes {
    public static final Type MATERIAL = new TypeToken<Material>() {
    }.getType();
    public static final Type STRING = new TypeToken<String>() {
    }.getType();

    private SupplementaryTypes() {
    }
}
