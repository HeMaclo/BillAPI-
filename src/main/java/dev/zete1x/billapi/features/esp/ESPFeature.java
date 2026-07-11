package dev.zete1x.billapi.features.esp;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import dev.zete1x.billapi.config.ConfigManager;
import dev.zete1x.billapi.config.KeyBindings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ESP фича - визуальное выделение игроков и мобов через встроенный Glowing эффект
 * 
 * Логика:
 * 1. Ищем всех игроков/мобов в мире
 * 2. Применяем им встроенный Glowing эффект (как от spectral arrow)
 * 3. Это позволяет видеть их сквозь стены и выделяет красивыми иконками
 * 
 * Преимущества:
 * - Встроено в Minecraft (нет нужны в кастомном рендеринге)
 * - Красиво выглядит и понятно
 * - Мало нагружает систему
 * - Сквозь стены видно (как у Spectral Arrow)
 */
public class ESPFeature {

    private MinecraftClient client = MinecraftClient.getInstance();
    private boolean enabled = false;
    private Set<Integer> glowingEntities = new HashSet<>(); // Тракируем какие сущности уже светятся

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
            
            // Если отключаем - убираем свечение со всех сущностей
            if (!enabled) {
                removeAllGlowing();
            }
        }

        // Если фича отключена - выходим
        if (!config.espEnabled) {
            return;
        }

        // Обновляем список светящихся сущностей
        updateGlowing(config);
    }

    /**
     * Обновляем свечение всех нужных сущностей
     */
    private void updateGlowing(ConfigManager.ModConfig config) {
        if (client.world == null || client.player == null) {
            return;
        }

        Set<Integer> currentGlowing = new HashSet<>();

        // Собираем всех нужных сущностей и даем им свечение
        for (Entity entity : client.world.getEntities()) {
            // Пропускаем сам игрока и невидимых
            if (entity == client.player || entity.isInvisible()) {
                continue;
            }

            // Проверяем типы сущностей которые нужно выделять
            boolean shouldGlow = false;

            if (config.espPlayers && entity instanceof PlayerEntity) {
                shouldGlow = true;
            } else if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;

                if (config.espMobs && living instanceof HostileEntity) {
                    shouldGlow = true;
                } else if (config.espAnimals && living instanceof AnimalEntity) {
                    shouldGlow = true;
                }
            }

            if (shouldGlow) {
                currentGlowing.add(entity.getId());
                applyGlowing(entity);
            }
        }

        // Убираем свечение с тех сущностей которые больше не нужны
        for (Integer entityId : glowingEntities) {
            if (!currentGlowing.contains(entityId)) {
                // Ищем сущность по ID и убираем свечение
                Entity entity = client.world.getEntityById(entityId);
                if (entity != null) {
                    removeGlowing(entity);
                }
            }
        }

        glowingEntities = currentGlowing;
    }

    /**
     * Применяем Glowing эффект к сущности
     * Это создает красивое свечение сквозь стены (как spectral arrow)
     * 
     * @param entity сущность для выделения
     */
    private void applyGlowing(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;

            // Есл�� уже светит - не применяем еще раз
            if (living.hasStatusEffect(StatusEffects.GLOWING)) {
                return;
            }

            // Применяем эффект Glowing с максимальной длительностью (практически вечный)
            // Длительность: Integer.MAX_VALUE = примерно 37 часов игрового времени
            living.addStatusEffect(
                    new net.minecraft.entity.effect.StatusEffectInstance(
                            StatusEffects.GLOWING,
                            Integer.MAX_VALUE,
                            0,
                            false,
                            false
                    )
            );
        }
    }

    /**
     * Убираем Glowing эффект с сущности
     * 
     * @param entity сущность
     */
    private void removeGlowing(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            living.removeStatusEffect(StatusEffects.GLOWING);
        }
    }

    /**
     * Убираем свечение со ВСЕХ сущностей (при отключении ESP)
     */
    private void removeAllGlowing() {
        if (client.world == null) {
            return;
        }

        for (Entity entity : client.world.getEntities()) {
            removeGlowing(entity);
        }

        glowingEntities.clear();
    }

    /**
     * Включить/выключить фичу
     */
    public void toggle() {
        enabled = !enabled;
        ConfigManager.ModConfig config = ConfigManager.getConfig();
        config.espEnabled = enabled;
        ConfigManager.save();

        if (!enabled) {
            removeAllGlowing();
        }
    }

    /**
     * Проверить включена ли фича
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Получить количество выделяемых сущностей
     */
    public int getGlowingCount() {
        return glowingEntities.size();
    }
}
