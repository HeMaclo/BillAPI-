package dev.zete1x.billapi.config;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

/**
 * Управление всеми биндами мода
 * Тут хранятся клавиши для каждой фичи
 * 
 * Логика: через KeyBindingHelper регистрируем кастомные кнопки
 */
public class KeyBindings {

    // AutoClicky биндинги
    public static final KeyBinding AUTOCLICKY_TOGGLE = register(
            "billapi.autoclicky.toggle",
            GLFW.GLFW_KEY_V,
            "BillAPI - AutoClicky"
    );

    public static final KeyBinding AUTOCLICKY_LMB = register(
            "billapi.autoclicky.lmb",
            GLFW.GLFW_KEY_UNKNOWN,
            "BillAPI - AutoClicky LMB"
    );

    public static final KeyBinding AUTOCLICKY_RMB = register(
            "billapi.autoclicky.rmb",
            GLFW.GLFW_KEY_UNKNOWN,
            "BillAPI - AutoClicky RMB"
    );

    // AimAssist биндинги
    public static final KeyBinding AIMASIST_TOGGLE = register(
            "billapi.aimasist.toggle",
            GLFW.GLFW_KEY_C,
            "BillAPI - AimAssist"
    );

    public static final KeyBinding AIMASIST_HOLD = register(
            "billapi.aimasist.hold",
            GLFW.GLFW_KEY_LEFT_ALT,
            "BillAPI - AimAssist Hold"
    );

    // ESP биндинги
    public static final KeyBinding ESP_TOGGLE = register(
            "billapi.esp.toggle",
            GLFW.GLFW_KEY_E,
            "BillAPI - ESP"
    );

    /**
     * Регистрируем новый кейбиндинг
     * @param name название кнопки
     * @param key дефолтная клавиша (GLFW константа)
     * @param category категория в настройках
     * @return зарегистрированный KeyBinding
     */
    private static KeyBinding register(String name, int key, String category) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                name,
                key,
                category
        ));
    }

    /**
     * Переключение состояния кнопки (по сути проверка нажатия)
     * @param binding проверяемый кейбиндинг
     * @return true если нажата
     */
    public static boolean isPressed(KeyBinding binding) {
        return binding.isPressed();
    }
}
