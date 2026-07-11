package dev.zete1x.billapi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import dev.zete1x.billapi.config.ConfigManager;
import dev.zete1x.billapi.features.autoclicky.AutoClickyFeature;
import dev.zete1x.billapi.features.aimasist.AimAssistFeature;
import dev.zete1x.billapi.features.esp.ESPFeature;

/**
 * Главная точка входа BillAPI мода
 * Версия: 1.0-A
 * Авторы: Zete1x_Dev
 * 
 * Логика: инициализируем все фичи и вешаем слушатели на тики
 */
public class BillAPI implements ClientModInitializer {

    public static final String MOD_ID = "billapi";
    public static final String MOD_VERSION = "1.0-A";

    private static AutoClickyFeature autoClickyFeature;
    private static AimAssistFeature aimAssistFeature;
    private static ESPFeature espFeature;

    @Override
    public void onInitializeClient() {
        // Загружаем конфиг (если существует)
        ConfigManager.load();

        // Инициализируем все фичи
        autoClickyFeature = new AutoClickyFeature();
        aimAssistFeature = new AimAssistFeature();
        espFeature = new ESPFeature();

        // Вешаем обработчик тиков для всех фич
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.world != null) {
                autoClickyFeature.onTick();
                aimAssistFeature.onTick();
            }
        });

        System.out.println("[BillAPI] Мод инициализирован! Версия: " + MOD_VERSION);
    }

    // Геттеры для фич (для гуи и других компонентов)
    public static AutoClickyFeature getAutoClickyFeature() {
        return autoClickyFeature;
    }

    public static AimAssistFeature getAimAssistFeature() {
        return aimAssistFeature;
    }

    public static ESPFeature getESPFeature() {
        return espFeature;
    }
}
