package dev.zete1x.billapi.features.aimasist;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import dev.zete1x.billapi.config.ConfigManager;
import dev.zete1x.billapi.config.KeyBindings;

/**
 * AimAssist фича - плавный и легитный наводсок на игроков
 * 
 * Логика:
 * 1. Ищем ближайшего игрока в радиусе
 * 2. Плавно наводимся на центр его головы
 * 3. Используем камеру для поворота (естественно)
 * 4. Работает с кнопкой HOLD или постоянно если toggle включен
 */
public class AimAssistFeature {

    private MinecraftClient client = MinecraftClient.getInstance();
    private boolean enabled = false;

    /**
     * Вызывается каждый тик
     */
    public void onTick() {
        ConfigManager.ModConfig config = ConfigManager.getConfig();

        // Проверяем нажатие кнопки включения
        if (KeyBindings.AIMASIST_TOGGLE.wasPressed()) {
            enabled = !enabled;
            config.aimAssistEnabled = enabled;
            ConfigManager.save();
        }

        // Если фича отключена - выходим
        if (!config.aimAssistEnabled) {
            return;
        }

        // Проверяем держим ли кнопку
        if (!KeyBindings.AIMASIST_HOLD.isPressed()) {
            return;
        }

        // Ищем ближайшего игрока и наводимся на него
        PlayerEntity targetPlayer = findNearestPlayer(config.aimAssistDistance);
        if (targetPlayer != null && client.player != null) {
            aimAtEntity(targetPlayer, config.aimAssistSensitivity, config.aimAssistSpeed);
        }
    }

    /**
     * Ищем ближайшего игрока в радиусе
     * @param maxDistance максимальная дистанция поиска
     * @return ближайший игрок или null
     */
    private PlayerEntity findNearestPlayer(float maxDistance) {
        if (client.world == null || client.player == null) {
            return null;
        }

        PlayerEntity nearestPlayer = null;
        double nearestDistance = maxDistance;

        // Пробегаем всех игроков в мире
        for (Entity entity : client.world.getEntities()) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;

                // Не считаем сам игрока
                if (player == client.player) {
                    continue;
                }

                // Проверяем дистанцию
                double distance = client.player.distanceTo(player);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestPlayer = player;
                }
            }
        }

        return nearestPlayer;
    }

    /**
     * Плавно наводимся на сущность
     * 
     * @param target цель для наводсока
     * @param sensitivity чувствительность (0-1, где 1 это мгновенный наводсок)
     * @param speed скорость плавного наводсока (тики, меньше = быстрее)
     */
    private void aimAtEntity(Entity target, float sensitivity, int speed) {
        if (client.player == null) {
            return;
        }

        // Получаем позицию головы цели
        Vec3d targetPos = target.getEyePos();
        Vec3d playerPos = client.player.getEyePos();

        // Считаем вектор направления
        Vec3d direction = targetPos.subtract(playerPos);
        double distance = direction.length();

        if (distance > 0) {
            // Нормализуем вектор
            direction = direction.normalize();

            // Считаем углы (yaw и pitch)
            double yaw = Math.atan2(direction.x, direction.z) * 180.0 / Math.PI;
            double pitch = Math.asin(-direction.y) * 180.0 / Math.PI;

            // Получаем текущие углы камеры
            float currentYaw = client.player.getYaw();
            float currentPitch = client.player.getPitch();

            // Считаем разницу
            float yawDelta = (float) (yaw - currentYaw);
            float pitchDelta = (float) (pitch - currentPitch);

            // Нормализуем углы (чтобы не прокручивалось на 360 градусов)
            while (yawDelta > 180) yawDelta -= 360;
            while (yawDelta < -180) yawDelta += 360;

            // Плавно поворачиваемся (используем sensitivity и speed)
            float smoothFactor = (sensitivity * speed) / 10f;
            smoothFactor = Math.min(smoothFactor, 1.0f); // Макс 1.0 это мгновенный поворот

            float newYaw = currentYaw + yawDelta * smoothFactor;
            float newPitch = Math.max(-90, Math.min(90, currentPitch + pitchDelta * smoothFactor));

            // Применяем новые углы
            client.player.setYaw(newYaw);
            client.player.setPitch(newPitch);

            // Поворачиваем и голову для полноты
            client.player.headYaw = newYaw;
        }
    }

    /**
     * Включить/выключить фичу
     */
    public void toggle() {
        enabled = !enabled;
        ConfigManager.ModConfig config = ConfigManager.getConfig();
        config.aimAssistEnabled = enabled;
        ConfigManager.save();
    }

    /**
     * Проверить включена ли фича
     */
    public boolean isEnabled() {
        return enabled;
    }
}
