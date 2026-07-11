package dev.zete1x.billapi.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

/**
 * Утилиты для рисования
 * Тут всякие хелпер методы для отрисовки боксов, линий и т.д.
 * 
 * Логика: используем Minecraft rendering API для рисования в мире
 */
public class RenderUtils {

    private static final MinecraftClient client = MinecraftClient.getInstance();

    /**
     * Рисуем куб (бокс) в мире
     * 
     * @param matrices матрица трансформации
     * @param x,y,z позиция бокса
     * @param width ширина
     * @param height высота
     * @param color цвет (0xARGB)
     */
    public static void drawBox(MatrixStack matrices, double x, double y, double z,
                               double width, double height, int color) {
        // Конвертируем цвет
        int a = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        // Нормализуем значения (0-1)
        float af = a / 255.0f;
        float rf = r / 255.0f;
        float gf = g / 255.0f;
        float bf = b / 255.0f;

        // Рисование реализуется через WorldRenderEvent
        // Тут только базовая структура
    }

    /**
     * Рисуем линию в мире
     * 
     * @param matrices матрица трансформации
     * @param x1,y1,z1 начало линии
     * @param x2,y2,z2 конец линии
     * @param color цвет (0xARGB)
     * @param width толщина линии
     */
    public static void drawLine(MatrixStack matrices, double x1, double y1, double z1,
                                double x2, double y2, double z2, int color, float width) {
        // Конвертируем цвет
        int a = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        // Нормализуем значения (0-1)
        float af = a / 255.0f;
        float rf = r / 255.0f;
        float gf = g / 255.0f;
        float bf = b / 255.0f;

        // Рисование реализуется через WorldRenderEvent
    }

    /**
     * Преобразуем экранные координаты в мировые
     * (нужно для интеграции рисования с GUI)
     */
    public static double[] screenToWorld(double screenX, double screenY) {
        // Тут будет логика конвертации если нужна
        return new double[]{screenX, screenY, 0};
    }
}
