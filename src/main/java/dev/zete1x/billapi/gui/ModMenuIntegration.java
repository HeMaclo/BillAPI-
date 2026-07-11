package dev.zete1x.billapi.gui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.zete1x.billapi.BillAPI;

/**
 * Интеграция с Mod Menu
 * Это позволяет пользователям открывать GUI мода прямо из Mod Menu
 * 
 * Логика: регистрируем наш GUI как конфиг экран
 */
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new BillAPIScreen(parent);
    }
}
