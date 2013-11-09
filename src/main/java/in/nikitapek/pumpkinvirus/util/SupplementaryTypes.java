package in.nikitapek.pumpkinvirus.util;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

@SuppressWarnings("rawtypes")
public final class SupplementaryTypes {
    public static final Type STRING = new TypeToken<String>() {
    }.getType();

    private SupplementaryTypes() {
    }
}
