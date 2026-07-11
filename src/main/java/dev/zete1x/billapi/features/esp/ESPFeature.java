package dev.zete1x.billapi.features.esp;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.joml.Matrix4f;
import dev.zete1x.billapi.config.ConfigManager;
import dev.zete1x.billapi.config.KeyBindings;

import java.util.ArrayList;
import java.util.List;

/**
 * ESP фича - визуальное выделение игроков и мобов сквозь стены
 * 
 * Логика:
 * 1. Ищем всех игроков/мобов в мире
 * 2. Рисуем боксы вокруг них
 * 3. Рисуется поверх других объектов (сквозь стены)
 * 
 * Примечание: для рисования нужны миксины, поэтому это базовая реализация
 */
public class ESPFeature {

    private MinecraftClient client = MinecraftClient.getInstance();
    private boolean enabled = false;
    private List<Entity> cachedEntities = new ArrayList<>();

    /**
     * Вызывается каждый тик
     */
    public void onTick() {
        ConfigManager.ModConfig config = ConfigManager.getConfig();

        // Проверяем нажатие кнопки включения
        if (KeyBindings.ESP_TOGGLE.wasPressed()) {
            enabled = !enabled;
            config.espEnabled = enabled;
            ConfigManager.save();
        }

        // Если фича отключена - выходим
        if (!config.espEnabled) {
            cachedEntities.clear();
            return;
        }

        // Обновляем кэш сущностей
        updateEntityCache(config);
    }

    /**
     * Обновляем список сущностей для отрисовки
     */
    private void updateEntityCache(ConfigManager.ModConfig config) {
        cachedEntities.clear();

        if (client.world == null || client.player == null) {
            return;
        }

        // Собираем всех нужных сущностей
        for (Entity entity : client.world.getEntities()) {
            // Пропускаем сам игрока и невидимых
            if (entity == client.player || entity.isInvisible()) {
                continue;
            }

            // Проверяем типы сущностей которые нужно выделять
            if (config.espPlayers && entity instanceof PlayerEntity) {
                cachedEntities.add(entity);
            } else if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;

                if (config.espMobs && living instanceof HostileEntity) {
                    cachedEntities.add(entity);
                } else if (config.espAnimals && living instanceof AnimalEntity) {
                    cachedEntities.add(entity);
                }
            }
        }
    }

    /**
     * Рисуем все боксы (вызывается из миксина)
     * 
     * @param matrices матрица трансформации для рисования
     * @param tickDelta дельта времени между кадрами
     */
    public void render(MatrixStack matrices, float tickDelta) {
        ConfigManager.ModConfig config = ConfigManager.getConfig();

        if (!config.espEnabled || client.player == null) {
            return;
        }

        // Рисуем боксы для каждой сущности
        for (Entity entity : cachedEntities) {
            drawEntityBox(entity, matrices, config.espBoxColor);
        }
    }

    /**
     * Рисуем бокс вокруг сущности
     * 
     * @param entity сущность для рисования
     * @param matrices матрица трансформации
     * @param color цвет в формате 0xARGB
     */
    private void drawEntityBox(Entity entity, MatrixStack matrices, int color) {
        // Получаем размеры hitbox сущности
        double x = entity.getX() - entity.getWidth() / 2;
        double y = entity.getY();
        double z = entity.getZ() - entity.getWidth() / 2;
        double width = entity.getWidth();
        double height = entity.getHeight();

        // Тут должен быть код рисования боксов через WorldRenderEvent
        // Это требует миксина, поэтому оставляем заготовку
        // Рисование реализуется через RenderUtils
    }

    /**
     * Получить кэш сущностей
     */
    public List<Entity> getCachedEntities() {
        return cachedEntities;
    }

    /**
     * Включить/выключить фичу
     */
    public void toggle() {
        enabled = !enabled;
        ConfigManager.ModConfig config = ConfigManager.getConfig();
        config.espEnabled = enabled;
        ConfigManager.save();
    }

    /**
     * Проверить включена ли фича
     */
    public boolean isEnabled() {
        return enabled;
    }
}
