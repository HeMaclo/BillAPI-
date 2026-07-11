package dev.zete1x.billapi.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Менеджер конфигов для мода
 * Сохраняет/загружает все настройки в JSON
 * 
 * Логика: используем GSON для сериализации/десериализации
 */
public class ConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_DIR = new File("config/billapi");
    private static final File CONFIG_FILE = new File(CONFIG_DIR, "settings.json");

    public static ModConfig currentConfig = new ModConfig();

    static {
        // Создаем директорию если её нет
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        }
    }

    /**
     * Загружаем конфиг из файла
     * Если файла нет - используем дефолтный конфиг
     */
    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                currentConfig = GSON.fromJson(reader, ModConfig.class);
                System.out.println("[BillAPI] Конфиг загружен!");
            } catch (IOException e) {
                System.err.println("[BillAPI] Ошибка при загрузке конфига!");
                e.printStackTrace();
                currentConfig = new ModConfig();
            }
        } else {
            // Если конфига нет - создаём дефолтный
            save();
        }
    }

    /**
     * Сохраняем конфиг в файл
     */
    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(currentConfig, writer);
            System.out.println("[BillAPI] Конфиг сохранен!");
        } catch (IOException e) {
            System.err.println("[BillAPI] Ошибка при сохранении конфига!");
            e.printStackTrace();
        }
    }

    /**
     * Конфигурация мода (все настройки тут)
     */
    public static class ModConfig {
        // AutoClicky
        public boolean autoClickyEnabled = false;
        public int autoClickyDelay = 8;
        public boolean autoClickyMode189 = false; // false = 1.8, true = 1.9+
        public boolean autoClickyLeftClick = true;
        public boolean autoClickyRightClick = false;

        // AimAssist
        public boolean aimAssistEnabled = false;
        public float aimAssistSensitivity = 0.5f;
        public float aimAssistDistance = 20.0f;
        public boolean aimAssistSmooth = true;
        public int aimAssistSpeed = 5;

        // ESP
        public boolean espEnabled = false;
        public boolean espPlayers = true;
        public boolean espMobs = true;
        public boolean espAnimals = false;
        public int espBoxColor = 0xFF00FF00; // Зеленый по дефолту
    }

    /**
     * Получить текущий конфиг
     */
    public static ModConfig getConfig() {
        return currentConfig;
    }
}
