package bonusDirectory;

import com.google.inject.AbstractModule;

public final class BonusEventHandlerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BonusEventHandler.class).toInstance(new BonusEventHandler());
    }
}
