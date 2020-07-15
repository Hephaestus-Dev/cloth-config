package me.shedaniel.clothconfig2.forge.gui.entries;

import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class IntegerListEntry extends TextFieldListEntry<Integer> {
    
    private static Function<String, String> stripCharacters = s -> {
        StringBuilder builder = new StringBuilder();
        char[] var2 = s.toCharArray();
        int var3 = var2.length;
        
        for (char c : var2)
            if (Character.isDigit(c) || c == '-')
                builder.append(c);
        
        return builder.toString();
    };
    private int minimum, maximum;
    private Consumer<Integer> saveConsumer;
    
    @ApiStatus.Internal
    @Deprecated
    public IntegerListEntry(ITextComponent fieldName, Integer value, ITextComponent resetButtonKey, Supplier<Integer> defaultValue, Consumer<Integer> saveConsumer) {
        super(fieldName, value, resetButtonKey, defaultValue);
        this.minimum = -Integer.MAX_VALUE;
        this.maximum = Integer.MAX_VALUE;
        this.saveConsumer = saveConsumer;
    }
    
    @ApiStatus.Internal
    @Deprecated
    public IntegerListEntry(ITextComponent fieldName, Integer value, ITextComponent resetButtonKey, Supplier<Integer> defaultValue, Consumer<Integer> saveConsumer, Supplier<Optional<ITextComponent[]>> tooltipSupplier) {
        this(fieldName, value, resetButtonKey, defaultValue, saveConsumer, tooltipSupplier, false);
    }
    
    @ApiStatus.Internal
    @Deprecated
    public IntegerListEntry(ITextComponent fieldName, Integer value, ITextComponent resetButtonKey, Supplier<Integer> defaultValue, Consumer<Integer> saveConsumer, Supplier<Optional<ITextComponent[]>> tooltipSupplier, boolean requiresRestart) {
        super(fieldName, value, resetButtonKey, defaultValue, tooltipSupplier, requiresRestart);
        this.minimum = -Integer.MAX_VALUE;
        this.maximum = Integer.MAX_VALUE;
        this.saveConsumer = saveConsumer;
    }
    
    @Override
    protected String stripAddText(String s) {
        return stripCharacters.apply(s);
    }
    
    @Override
    protected void textFieldPreRender(TextFieldWidget widget) {
        try {
            double i = Integer.parseInt(textFieldWidget.getText());
            if (i < minimum || i > maximum)
                widget.setTextColor(16733525);
            else
                widget.setTextColor(14737632);
        } catch (NumberFormatException ex) {
            widget.setTextColor(16733525);
        }
    }
    
    @Override
    protected boolean isMatchDefault(String text) {
        return getDefaultValue().isPresent() && text.equals(defaultValue.get().toString());
    }
    
    @Override
    public void save() {
        if (saveConsumer != null)
            saveConsumer.accept(getValue());
    }
    
    public IntegerListEntry setMaximum(int maximum) {
        this.maximum = maximum;
        return this;
    }
    
    public IntegerListEntry setMinimum(int minimum) {
        this.minimum = minimum;
        return this;
    }
    
    @Override
    public Integer getValue() {
        try {
            return Integer.valueOf(textFieldWidget.getText());
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public Optional<ITextComponent> getError() {
        try {
            int i = Integer.parseInt(textFieldWidget.getText());
            if (i > maximum)
                return Optional.of(new TranslationTextComponent("text.cloth-config.error.too_large", maximum));
            else if (i < minimum)
                return Optional.of(new TranslationTextComponent("text.cloth-config.error.too_small", minimum));
        } catch (NumberFormatException ex) {
            return Optional.of(new TranslationTextComponent("text.cloth-config.error.not_valid_number_int"));
        }
        return super.getError();
    }
}