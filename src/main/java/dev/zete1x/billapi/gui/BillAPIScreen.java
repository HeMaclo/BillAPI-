package dev.zete1x.billapi.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import dev.zete1x.billapi.config.ConfigManager;

/**
 * Главный GUI экран для настроек BillAPI
 * 
 * Логика: показываем все фичи и их параметры которые можно менять
 * Используем кнопки, ползунки и текстовые поля для изменения значений
 */
public class BillAPIScreen extends Screen {

    private final Screen parent;
    private ConfigManager.ModConfig config;

    // Кнопки для переключения фич
    private ButtonWidget autoClickyToggle;
    private ButtonWidget aimAssistToggle;
    private ButtonWidget espToggle;

    // Слайдеры для параметров
    private SliderWidget autoClickyDelaySlider;
    private SliderWidget aimAssistSensitivitySlider;
    private SliderWidget aimAssistSpeedSlider;

    public BillAPIScreen(Screen parent) {
        super(Text.literal("BillAPI Настройки 🎯"));
        this.parent = parent;
        this.config = ConfigManager.getConfig();
    }

    @Override
    protected void init() {
        super.init();

        // Очищаем старые виджеты
        this.clearChildren();

        int y = 20;
        int x = 20;
        int buttonWidth = 150;
        int buttonHeight = 20;
        int spacing = 30;

        // ===== AutoClicky Секция =====
        // Название секции
        this.addDrawableChild(
                new net.minecraft.client.gui.widget.EmptyWidget(x, y, 200, 20)
        );
        y += 25;

        // Кнопка включения AutoClicky
        autoClickyToggle = ButtonWidget.builder(
                Text.literal("AutoClicky: " + (config.autoClickyEnabled ? "§aВКЛ" : "§cВЫКЛ")),
                button -> {
                    config.autoClickyEnabled = !config.autoClickyEnabled;
                    updateButtons();
                    ConfigManager.save();
                }
        )
        .dimensions(x, y, buttonWidth, buttonHeight)
        .build();
        this.addDrawableChild(autoClickyToggle);
        y += spacing;

        // Слайдер для задержки AutoClicky
        autoClickyDelaySlider = new SliderWidget(x, y, buttonWidth, buttonHeight,
                Text.literal("Задержка: " + config.autoClickyDelay),
                (double) config.autoClickyDelay / 20.0) {
            @Override
            protected void updateMessage() {
                config.autoClickyDelay = (int) (this.value * 20);
                this.setMessage(Text.literal("Задержка: " + config.autoClickyDelay));
            }

            @Override
            protected void applyValue() {
                ConfigManager.save();
            }
        };
        this.addDrawableChild(autoClickyDelaySlider);
        y += spacing;

        // Кнопка выбора режима (1.8/1.9+)
        ButtonWidget modeToggle = ButtonWidget.builder(
                Text.literal("Режим: " + (config.autoClickyMode189 ? "1.8" : "1.9+")),
                button -> {
                    config.autoClickyMode189 = !config.autoClickyMode189;
                    button.setMessage(Text.literal("Режим: " + (config.autoClickyMode189 ? "1.8" : "1.9+")));
                    ConfigManager.save();
                }
        )
        .dimensions(x, y, buttonWidth, buttonHeight)
        .build();
        this.addDrawableChild(modeToggle);
        y += spacing;

        // Кнопки для ЛМК и ПМК
        ButtonWidget lmbToggle = ButtonWidget.builder(
                Text.literal("ЛМК: " + (config.autoClickyLeftClick ? "§aВКЛ" : "§cВЫКЛ")),
                button -> {
                    config.autoClickyLeftClick = !config.autoClickyLeftClick;
                    button.setMessage(Text.literal("ЛМК: " + (config.autoClickyLeftClick ? "§aВКЛ" : "§cВЫКЛ")));
                    ConfigManager.save();
                }
        )
        .dimensions(x, y, buttonWidth / 2 - 5, buttonHeight)
        .build();
        this.addDrawableChild(lmbToggle);

        ButtonWidget rmbToggle = ButtonWidget.builder(
                Text.literal("ПМК: " + (config.autoClickyRightClick ? "§aВКЛ" : "§cВЫКЛ")),
                button -> {
                    config.autoClickyRightClick = !config.autoClickyRightClick;
                    button.setMessage(Text.literal("ПМК: " + (config.autoClickyRightClick ? "§aВКЛ" : "§cВЫКЛ")));
                    ConfigManager.save();
                }
        )
        .dimensions(x + buttonWidth / 2 + 5, y, buttonWidth / 2 - 5, buttonHeight)
        .build();
        this.addDrawableChild(rmbToggle);
        y += spacing + 20;

        // ===== AimAssist Секция =====
        aimAssistToggle = ButtonWidget.builder(
                Text.literal("AimAssist: " + (config.aimAssistEnabled ? "§aВКЛ" : "§cВЫКЛ")),
                button -> {
                    config.aimAssistEnabled = !config.aimAssistEnabled;
                    updateButtons();
                    ConfigManager.save();
                }
        )
        .dimensions(x, y, buttonWidth, buttonHeight)
        .build();
        this.addDrawableChild(aimAssistToggle);
        y += spacing;

        // Слайдер чувствительности
        aimAssistSensitivitySlider = new SliderWidget(x, y, buttonWidth, buttonHeight,
                Text.literal("Чувствительность: " + String.format("%.2f", config.aimAssistSensitivity)),
                config.aimAssistSensitivity) {
            @Override
            protected void updateMessage() {
                config.aimAssistSensitivity = (float) this.value;
                this.setMessage(Text.literal("Чувствительность: " + String.format("%.2f", config.aimAssistSensitivity)));
            }

            @Override
            protected void applyValue() {
                ConfigManager.save();
            }
        };
        this.addDrawableChild(aimAssistSensitivitySlider);
        y += spacing;

        // Слайдер скорости
        aimAssistSpeedSlider = new SliderWidget(x, y, buttonWidth, buttonHeight,
                Text.literal("Скорость: " + config.aimAssistSpeed),
                (double) config.aimAssistSpeed / 20.0) {
            @Override
            protected void updateMessage() {
                config.aimAssistSpeed = (int) (this.value * 20);
                this.setMessage(Text.literal("Скорость: " + config.aimAssistSpeed));
            }

            @Override
            protected void applyValue() {
                ConfigManager.save();
            }
        };
        this.addDrawableChild(aimAssistSpeedSlider);
        y += spacing + 20;

        // ===== ESP Секция =====
        espToggle = ButtonWidget.builder(
                Text.literal("ESP: " + (config.espEnabled ? "§aВКЛ" : "§cВЫКЛ")),
                button -> {
                    config.espEnabled = !config.espEnabled;
                    updateButtons();
                    ConfigManager.save();
                }
        )
        .dimensions(x, y, buttonWidth, buttonHeight)
        .build();
        this.addDrawableChild(espToggle);
        y += spacing;

        // Кнопки выбора типов сущностей
        ButtonWidget playersToggle = ButtonWidget.builder(
                Text.literal("Игроки: " + (config.espPlayers ? "§aВКЛ" : "§cВЫКЛ")),
                button -> {
                    config.espPlayers = !config.espPlayers;
                    button.setMessage(Text.literal("Игроки: " + (config.espPlayers ? "§aВКЛ" : "§cВЫКЛ")));
                    ConfigManager.save();
                }
        )
        .dimensions(x, y, buttonWidth, buttonHeight)
        .build();
        this.addDrawableChild(playersToggle);
        y += spacing;

        ButtonWidget mobsToggle = ButtonWidget.builder(
                Text.literal("Мобы: " + (config.espMobs ? "§aВКЛ" : "§cВЫКЛ")),
                button -> {
                    config.espMobs = !config.espMobs;
                    button.setMessage(Text.literal("Мобы: " + (config.espMobs ? "§aВКЛ" : "§cВЫКЛ")));
                    ConfigManager.save();
                }
        )
        .dimensions(x, y, buttonWidth, buttonHeight)
        .build();
        this.addDrawableChild(mobsToggle);
        y += spacing;

        ButtonWidget animalsToggle = ButtonWidget.builder(
                Text.literal("Животные: " + (config.espAnimals ? "§aВКЛ" : "§cВЫКЛ")),
                button -> {
                    config.espAnimals = !config.espAnimals;
                    button.setMessage(Text.literal("Животные: " + (config.espAnimals ? "§aВКЛ" : "§cВЫКЛ")));
                    ConfigManager.save();
                }
        )
        .dimensions(x, y, buttonWidth, buttonHeight)
        .build();
        this.addDrawableChild(animalsToggle);
        y += spacing + 20;

        // Кнопка "Назад"
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Назад"), button -> {
            this.client.setScreen(parent);
        })
        .dimensions(x, this.height - 30, buttonWidth, 20)
        .build());

        // Кнопка "Сбросить настройки"
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Сбросить"), button -> {
            ConfigManager.currentConfig = new ConfigManager.ModConfig();
            ConfigManager.save();
            this.init(this.client, this.width, this.height);
        })
        .dimensions(x + buttonWidth + 20, this.height - 30, buttonWidth, 20)
        .build());
    }

    /**
     * Обновляем текст на кнопках когда меняется состояние
     */
    private void updateButtons() {
        if (autoClickyToggle != null) {
            autoClickyToggle.setMessage(Text.literal(
                    "AutoClicky: " + (config.autoClickyEnabled ? "§aВКЛ" : "§cВЫКЛ")
            ));
        }
        if (aimAssistToggle != null) {
            aimAssistToggle.setMessage(Text.literal(
                    "AimAssist: " + (config.aimAssistEnabled ? "§aВКЛ" : "§cВЫКЛ")
            ));
        }
        if (espToggle != null) {
            espToggle.setMessage(Text.literal(
                    "ESP: " + (config.espEnabled ? "§aВКЛ" : "§cВЫКЛ")
            ));
        }
    }

    @Override
    public void render(net.minecraft.client.gui.DrawContext context, int mouseX, int mouseY, float delta) {
        // Рисуем фон
        this.renderBackground(context, mouseX, mouseY, delta);

        // Рисуем заголовок
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.literal("§l§6BillAPI Настройки 🎯"),
                this.width / 2,
                10,
                0xFFFFFF
        );

        // Рисуем секции
        context.drawTextWithShadow(
                this.textRenderer,
                Text.literal("§e=== AutoClicky ==="),
                20,
                20,
                0xFFFFFF
        );

        context.drawTextWithShadow(
                this.textRenderer,
                Text.literal("§e=== AimAssist ==="),
                20,
                140,
                0xFFFFFF
        );

        context.drawTextWithShadow(
                this.textRenderer,
                Text.literal("§e=== ESP ==="),
                20,
                240,
                0xFFFFFF
        );

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
