package net.jimbot6000.blockentityextendedrendering.gui;

import org.thinkingstudio.obsidianui.Position;
import org.thinkingstudio.obsidianui.option.SpruceDoubleOption;
import org.thinkingstudio.obsidianui.screen.SpruceScreen;
import org.thinkingstudio.obsidianui.widget.SpruceButtonWidget;
import org.thinkingstudio.obsidianui.widget.container.SpruceOptionListWidget;
import net.jimbot6000.blockentityextendedrendering.BlockEntityExtendedRendering;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.Properties;

public class BEERConfigScreen extends SpruceScreen {
    private final Screen parent;

    private final Properties config = new Properties();

    public BEERConfigScreen(Screen screen) {
        super(Text.translatable("beer.screen.config"));
        parent = screen;
    }

    private int getTextHeight() {
        return (5 + this.textRenderer.fontHeight) * 3 + 5;
    }

    @Override
    protected void init() {
        super.init();
        BlockEntityExtendedRendering.CONFIG.writeTo(config);
        int bottomCenter = this.width / 2 - 50;

        // Button list.
        SpruceOptionListWidget list = new SpruceOptionListWidget(Position.of(0, 22), this.width, this.height - 35 - 22);
        list.addSingleOptionEntry(new SpruceDoubleOption("beer.option.distance",
                32.0, 512.0, 32.f,
                () -> Double.valueOf(config.getProperty(BEERConfig.BLOCK_ENTITY_RENDER_DISTANCE_KEY)),
                newValue -> config.setProperty(BEERConfig.BLOCK_ENTITY_RENDER_DISTANCE_KEY, String.valueOf(newValue.intValue())),
                option -> option.getDisplayText(Text.of(String.valueOf(config.getProperty(BEERConfig.BLOCK_ENTITY_RENDER_DISTANCE_KEY)))),
                Text.of("This is the distance from which block entities will be visible.")));

        this.addDrawableChild(list);

        this.addDrawableChild(new SpruceButtonWidget(Position.of(bottomCenter - 104, this.height - 27), 100, 20, ScreenTexts.CANCEL, button -> this.close()));
        this.addDrawableChild(new SpruceButtonWidget(Position.of(bottomCenter, this.height - 27), 100, 20, Text.translatable("beer.text.apply"), button -> this.applyChanges()));
        this.addDrawableChild(new SpruceButtonWidget(Position.of(bottomCenter + 104, this.height - 27), 100, 20, ScreenTexts.DONE, button -> {
            this.applyChanges();
            close();
        }));
    }

    public void applyChanges() {
        BlockEntityExtendedRendering.CONFIG.readFrom(config);
        BlockEntityExtendedRendering.CONFIG.save();
    }

    @Override
    public void renderTitle(DrawContext graphics, int mouseX, int mouseY, float delta) {
        graphics.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 8, 16777215);
    }
}
