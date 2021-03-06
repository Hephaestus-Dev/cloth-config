package me.shedaniel.clothconfig2.gui.entries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class StringListEntry extends TextFieldListEntry<String> {
    
    private Consumer<String> saveConsumer;
    
    @ApiStatus.Internal
    @Deprecated
    public StringListEntry(Text fieldName, String value, Text resetButtonKey, Supplier<String> defaultValue, Consumer<String> saveConsumer) {
        super(fieldName, value, resetButtonKey, defaultValue);
        this.saveConsumer = saveConsumer;
    }
    
    @ApiStatus.Internal
    @Deprecated
    public StringListEntry(Text fieldName, String value, Text resetButtonKey, Supplier<String> defaultValue, Consumer<String> saveConsumer, Supplier<Optional<Text[]>> tooltipSupplier) {
        this(fieldName, value, resetButtonKey, defaultValue, saveConsumer, tooltipSupplier, false);
    }
    
    @ApiStatus.Internal
    @Deprecated
    public StringListEntry(Text fieldName, String value, Text resetButtonKey, Supplier<String> defaultValue, Consumer<String> saveConsumer, Supplier<Optional<Text[]>> tooltipSupplier, boolean requiresRestart) {
        super(fieldName, value, resetButtonKey, defaultValue, tooltipSupplier, requiresRestart);
        this.saveConsumer = saveConsumer;
    }
    
    @Override
    public String getValue() {
        return textFieldWidget.getText();
    }
    
    @Override
    public void save() {
        if (saveConsumer != null)
            saveConsumer.accept(getValue());
    }
    
    @Override
    protected boolean isMatchDefault(String text) {
        return getDefaultValue().isPresent() && text.equals(getDefaultValue().get());
    }
    
}
