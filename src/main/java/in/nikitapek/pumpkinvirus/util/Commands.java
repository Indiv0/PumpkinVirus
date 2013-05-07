package in.nikitapek.pumpkinvirus.util;

import com.amshulman.mbapi.util.PermissionsEnum;

public enum Commands implements PermissionsEnum {
    PUMPKINVIRUS;

    @Override
    public String getPrefix() {
        return "pumpkinvirus.";
    }
}
