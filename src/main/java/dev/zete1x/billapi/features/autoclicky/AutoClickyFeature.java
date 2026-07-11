package dev.zete1x.billapi.features.autoclicky;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import dev.zete1x.billapi.config.ConfigManager;
import dev.zete1x.billapi.config.KeyBindings;

/**
 * AutoClicky фича - автоматические удары по мобам/игрокам при наведении
 * 
 * Логика:
 * 1. Проверяем наведение на сущность (через raycast)
 * 2. Если есть сущность - наносим удар
 * 3. Соблюдаем задержку между ударами
 * 4. Поддерживаем 1.8 и 1.9+ режимы
 */
public class AutoClickyFeature {

    private long lastClickTime = 0;
    private MinecraftClient client = MinecraftClient.getInstance();

    /**
     * Вызывается каждый тик
     * Проверяем нужно ли кликать
     */
    public void onTick() {
        ConfigManager.ModConfig config = ConfigManager.getConfig();

        // Если фича отключена - выходим
        if (!config.autoClickyEnabled) {
            return;
        }

        // Проверяем нажатие кнопки включения
        if (KeyBindings.AUTOCLICKY_TOGGLE.wasPressed()) {
            config.autoClickyEnabled = false;
            ConfigManager.save();
            return;
        }

        // Проверяем наведение на сущность
        if (client.crosshairTarget instanceof EntityHitResult) {
            EntityHitResult hitResult = (EntityHitResult) client.crosshairTarget;
            Entity entity = hitResult.getEntity();

            // Проверяем что это не сам игрок
            if (entity != client.player && entity != null) {
                // Проверяем время (задержка между кликами)
                long currentTime = System.currentTimeMillis();
                long delayMs = calculateDelay(config.autoClickyDelay, config.autoClickyMode189);

                if (currentTime - lastClickTime >= delayMs) {
                    // Наносим удар
                    if (config.autoClickyLeftClick) {
                        client.player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
                        client.interactionManager.attackEntity(client.player, entity);
                    }

                    if (config.autoClickyRightClick) {
                        // Правый клик (если нужно, например для щита)
                        client.interactionManager.interactEntity(client.player, entity, net.minecraft.util.Hand.MAIN_HAND);
                    }

                    lastClickTime = currentTime;
                }
            }
        }
    }

    /**
     * Рассчитываем задержку между кликами
     * В 1.8 режиме - минимальная задержка (быстро)
     * В 1.9+ режиме - зависит от значения delay (в тиках)
     * 
     * @param delay значение задержки (в тиках)
     * @param is18Mode режим 1.8?
     * @return задержка в миллисекундах
     */
    private long calculateDelay(int delay, boolean is18Mode) {
        if (is18Mode) {
            // 1.8 режим - максимум скорости (~3 тика, 150ms)
            return 50;
        } else {
            // 1.9+ режим - конвертируем тики в миллисекунды
            // 1 тик = 50ms
            return delay * 50L;
        }
    }

    /**
     * Включить/выключить фичу
     */
    public void toggle() {
        ConfigManager.ModConfig config = ConfigManager.getConfig();
        config.autoClickyEnabled = !config.autoClickyEnabled;
        ConfigManager.save();
    }

    /**
     * Проверить включена ли фича
     */
    public boolean isEnabled() {
        return ConfigManager.getConfig().autoClickyEnabled;
    }
}
