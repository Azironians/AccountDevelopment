package main;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import javafx.stage.Stage;

public final class AGameModule extends AbstractModule {

    private final AGame aGame;

    AGameModule(final AGame aGame) {
        this.aGame = aGame;
    }

    @Override
    protected final void configure() {
        bind(AGame.class).toInstance(aGame);
        bind(Stage.class).asEagerSingleton();
        bindConstant().annotatedWith(Names.named("icon path")).to("file:src\\resources\\Triggers\\Icon.png");
        bindConstant().annotatedWith(Names.named("build number")).to( "Build 1.0.0.0.0.0.7");
        requestStaticInjection(AGame.class);
    }
}
