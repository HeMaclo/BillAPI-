package dev.zete1x.billapi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.network.ClientPlayerEntity;

/**
 * Миксин для хука в PlayerEntity
 * Используется для срабатывания нашего функционала на определенных событиях
 * 
 * Логика: мы инжектим код в нужные места где происходят события
 * (например клик, поворот камеры, и т.д.)
 */
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerInteractionMixin {

    /**
     * Хук в метод Movement для проверки нашего функционала каждый тик
     * Это позволяет нам изменять поведение игрока в реальном времени
     */
    @Inject(method = "tick", at = @At("HEAD"))
    private void onPlayerTick(CallbackInfo ci) {
        // Тут можно добавить дополнительный функционал если понадобится
        // На данный момент все работает через ClientTickEvents в BillAPI.java
    }
}
