package me.shedaniel.clothconfig2.impl.builders;

import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public abstract class FieldBuilder<T, A extends AbstractConfigListEntry> {
    @NotNull private final Text fieldNameKey;
    @NotNull private final Text resetButtonKey;
    protected boolean requireRestart = false;
    @Nullable protected Supplier<T> defaultValue = null;
    @Nullable protected Function<T, Optional<Text>> errorSupplier;
    
    protected FieldBuilder(Text resetButtonKey, Text fieldNameKey) {
        this.resetButtonKey = Objects.requireNonNull(resetButtonKey);
        this.fieldNameKey = Objects.requireNonNull(fieldNameKey);
    }
    
    @Nullable
    public final Supplier<T> getDefaultValue() {
        return defaultValue;
    }
    
    @SuppressWarnings("rawtypes")
    @Deprecated
    public final AbstractConfigListEntry buildEntry() {
        return build();
    }
    
    @NotNull
    public abstract A build();
    
    @NotNull
    public final Text getFieldNameKey() {
        return fieldNameKey;
    }
    
    @NotNull
    public final Text getResetButtonKey() {
        return resetButtonKey;
    }
    
    public boolean isRequireRestart() {
        return requireRestart;
    }
    
    public void requireRestart(boolean requireRestart) {
        this.requireRestart = requireRestart;
    }
    
}
